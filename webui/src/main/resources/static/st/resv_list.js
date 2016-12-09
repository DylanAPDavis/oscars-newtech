var netViz;         // Network map HTML container
var networkMap;     // Network map element
var netData;        // Data in the map

var netPorts = [];      // All network ports
var vizLinks = [];      // All network links

var filteredConnections = [];   // All (filtered) circuit reservations
var filteredBandwidthValues = [];   // All (filtered) bandwidth reservations

function initializeNetwork()
{
    netViz = document.getElementById('networkVisualization');
    netPorts = [];
    vizLinks = [];

    // Identify the network ports
    loadJSON("/viz/listPorts", function (response)
    {
        var ports = JSON.parse(response);
        netPorts = ports;
    });

    loadJSON("/viz/topology/multilayer", function (response)
    {
        var json_data = JSON.parse(response);
        var allLinks = json_data["edges"];

        for(var e = 0; e < allLinks.length; e++)
        {
            var edge = allLinks[e];

            if(edge.from !== null && edge.to !== null)
                vizLinks.push(edge);
        }

        var netOptions = {
            autoResize: true,
            width: '100%',
            height: '400px',
            interaction: {
                hover: false,
                navigationButtons: false,
                zoomView: false,
                dragView: false,
                multiselect: false,
                selectable: false,
            },
            physics: {
                stabilization: true,
            },
            nodes: {
                shape: 'dot',
                color: {background: "white"},
            }
        };

        // create an array with nodes
        var nodes = new vis.DataSet(json_data['nodes']);
        var edges = new vis.DataSet(json_data['edges']);

        // create a network
        netData = {
            nodes: nodes,
            edges: edges,
        };

        networkMap = new vis.Network(netViz, netData, netOptions);

        displayLinkConsumption();

        // Listener for when network is loading and stabilizing
        networkMap.on("stabilizationProgress", function(params) {
            var maxWidth = 100;
            var minWidth = 0;
            var widthFactor = params.iterations/params.total;
            var width = Math.max(minWidth,maxWidth * widthFactor);

            document.getElementById('progressBar').style.width = width + '%';
            document.getElementById('progressVal').innerHTML = Math.round(widthFactor*100) + '%';
        });

        networkMap.once("stabilizationIterationsDone", function() {
            document.getElementById('progressVal').innerHTML = '100%';
            document.getElementById('progressBar').style.width = '496px';
            document.getElementById('loadingBarDiv').style.opacity = 0;

            // really clean the dom element
            setTimeout(function () {document.getElementById('loadingBarDiv').style.display = 'none';}, 500);
        });
    });
}

function displayLinkConsumption()
{
    var theEdges = netData.edges;

    console.log("Displaying: " + theEdges.length);
}

// Retrieves and stores the full set of ReservedBW
function getAllReservedBWs()
{
    console.log("Updating Topology Links");
    setTimeout(getAllReservedBWs, 30000);   // Updates every 30 seconds

    var connectionIds = [];
    for(var c = 0; c < filteredConnections.length; c++)
        connectionIds.push(filteredConnections[c].connectionId);

    console.log("IDs: " + connectionIds);

    $.ajax({
            type: "POST",
            url: "/topology/reservedbw",
            data: connectionIds,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (response)
            {
                console.log(response);
            }
    });


        /*var relevantBwItems = JSON.parse(response);

        var newBwItems = [];
        for(var bw = 0; bw < relevantBwItems.length; bw++)
        {
            var bwItem = relevantBwItems[bw];
            var index = $.inArray(bwItem, filteredBandwidthValues);

            if(index === -1)    // New Item
                newBwItems.push(bwItem);
             else
             {

             }

             console.log("URN: " + bwItem.urn + ", ConnID: " + oneBwDTO.containerConnectionId);
        }

        console.log("Relevant: " + relevantBwItems);*/

    /* Potentially use this to identify colors for links */
      /*java.awt.image.IndexColorModel icm = ColorMap.JET;
         ColorMap ecm = new ColorMap(minBw, maxBw, icm); 
         utilization.keySet().forEach(edge -> {
             Long value = utilization.get(edge); 

            String rgb = this.toWeb(ecm.getColor(value.doubleValue())); 
            VizEdge ve = VizEdge.builder()                 .from(a).to(z).title(title).label(label).value(value.intValue())
                     .arrows("to").arrowStrikethrough(false).color(rgb) 
                    .build(); 

    private String toWeb(Color c) { 
      String rgb = Integer.toHexString(c.getRGB()); 
      rgb = "#" + rgb.substring(2, rgb.length()); 
      return rgb;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     */
}

function initializeConnectionList()
{
    filteredConnections = [];

    loadJSON("/resv/list/allconnections", function (response)
    {
        filteredConnections = JSON.parse(response);

        var listBody = document.getElementById('listBody');

        for(var c = 0; c < filteredConnections.length; c++)
        {
            var theConnection = filteredConnections[c];
            console.log("Connection: " + theConnection);

            var tr = document.createElement('tr');
            tr.setAttribute("class", "accordion-toggle");
            tr.setAttribute("data-toggle", "collapse");
            tr.setAttribute("data-target", "#accordion_" + theConnection.connectionId);

            for(var col = 1; col <= 6; col++)
            {
                var td = document.createElement('td');
                tr.appendChild(td);

                if(col === 1)
                    td.innerHTML = theConnection.connectionId;
                else if(col === 2)
                    td.innerHTML = theConnection.specification.description;
                else if(col === 3)
                {
                    var div1 = document.createElement('div');
                    var div2 = document.createElement('div');
                    var div3 = document.createElement('div');

                    div1.innerHTML = theConnection.states.resv;
                    div2.innerHTML = theConnection.states.prov;
                    div3.innerHTML = theConnection.states.oper;

                    td.appendChild(div1);
                    td.appendChild(div2);
                    td.appendChild(div3);
                }
                else if(col === 4)
                {
                    var div1 = document.createElement('div');
                    var div2 = document.createElement('div');

                    var startDate = new Date();
                    var endDate = new Date();

                    startDate.setTime(theConnection.specification.scheduleSpec.startDates);
                    endDate.setTime(theConnection.specification.scheduleSpec.endDates);

                    div1.innerHTML = "Start: " + startDate;
                    div2.innerHTML = "End: " + endDate;

                    td.appendChild(div1);
                    td.appendChild(div2);
                }
                else if(col === 5)
                    td.innerHTML = theConnection.specification.username;
                else
                    td.innerHTML = "Feature not yet supported";
            }

            var trHidden = document.createElement('tr');
            var tdHidden = document.createElement('td');
            tdHidden.setAttribute("colspan", "6");
            tdHidden.setAttribute("class", "hiddenRow");
            var divHidden = document.createElement('div');
            divHidden.setAttribute("class", "accordion-body collapse");
            divHidden.setAttribute("id", "accordion_" + theConnection.connectionId);
            divHidden.setAttribute("onshow", "showDetails(this)");
            var bLabel = document.createElement('b');
            bLabel.innerHTML = "Reservation Details:";
            var divRezDisp = document.createElement('div');
            divRezDisp.setAttribute("id", "resViz_" + theConnection.connectionId);
            divRezDisp.setAttribute("class", "panel-body collapse collapse in" + theConnection.connectionId);

            var noRoute = document.createElement('h5');
            noRoute.setAttribute("style", "color: #600000");
            noRoute.id = "emptyViz_" + theConnection.connectionId;
            noRoute.innerHTML = "No route information to display.";

            divHidden.appendChild(bLabel);
            divHidden.appendChild(divRezDisp);
            divHidden.appendChild(noRoute);
            tdHidden.appendChild(divHidden);
            trHidden.appendChild(tdHidden);
            listBody.appendChild(tr);
            listBody.appendChild(trHidden);
        }

        getAllReservedBWs();
    });
}

function showDetails(connectionToShow)
{
    var connID = connectionToShow.id.split("accordion_");

    drawReservation(connID[1]);
}

function clearDetails(connectionToShow)
{
    var connID = connectionToShow.id.split("accordion_");

    clearReservation(connID[1]);
}

var drawReservation = function(connectionID)
{
    var vizName = "resViz_" + connectionID;
    var emptyVizName = "emptyViz_" + connectionID;

    var vizElement = document.getElementById(vizName);
    var emptyVizElement = document.getElementById(emptyVizName);

    loadJSON("/viz/connection/" + connectionID, function (response)
    {
        var json_data = JSON.parse(response);
        console.log(json_data);

        edges = json_data.edges;
        nodes = json_data.nodes;

        if(edges.length === 0 || nodes.length === 0)
        {
            $(vizElement).hide();
            $(emptyVizElement).show();
            return;
        }
        else
        {
            $(vizElement).show();
            $(emptyVizElement).hide();
        }

        // Parse JSON string into object
        var resOptions = {
            autoResize: true,
            width: '100%',
            height: '100%',
            interaction: {
                hover: true,
                navigationButtons: false,
                zoomView: false,
                dragView: false,
                multiselect: false,
                selectable: true,
            },
            physics: {
                stabilization: true,
            },
            nodes: {
                shape: 'dot',
                color: {background: "white"},
            }
        };

        reservation_viz = make_network(json_data, vizElement, resOptions, vizName);
    });
};

var clearReservation = function(vizElement)
{
    //TODO: Figure out how to clear/destroy the reservation view so things don't get slow
    console.log("Need to figure out how to clear/destroy the reservation view so things don't get slow!");
}

function trigger_form_changes(is_resv, selected_an_edge, selected_a_node, is_selected_node_plain, nodeId, edgeId)
{
    //TODO: Implement some actions when parts of reservation viz are selected
    ;
}

$(document).ready(function ()
{
    /*initializeNetwork();

    initializeConnectionList();*/

    console.log("Testing new GET METHODS:")

    // Example 1
    console.log("Testing Full Device2Port Map");
    loadJSON("/topology/deviceportmap/full", function (response)
    {
        var fullPortMap = JSON.parse(response);

        console.log(fullPortMap);
        console.log(fullPortMap["denv-cr5"]);
    });

/*
    // Example 2
    console.log("Testing Single Device2Port Map");
    var deviceURN = "chic-cr5";

    loadJSON("/topology/portdevicemap/" + deviceURN, function (response)
    {
        var portSet = JSON.parse(response);

        console.log(portSet);
    });

    // Example 3
    console.log("Testing Single Device2Port Map - with a bad URN");
    var deviceURN = "madeup-device-cr5";

    loadJSON("/topology/portdevicemap/" + deviceURN, function (response)
    {
        var portSet = JSON.parse(response);

        console.log(portSet);
    });

    // Example 4
    console.log("Testing Full Port2Device Map");
    loadJSON("/topology/portdevicemap/full", function (response)
    {
        var usableResponse = JSON.parse(response);

        console.log(usableResponse);
        console.log(usableResponse["denv-cr5:1/1/1"]);
    });
*/

});