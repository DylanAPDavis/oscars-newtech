<#-- @ftlvariable name="lsps" type="java.util.List<net.es.oscars.dto.pss.params.mx.MxLsp>" -->


<#list mxlsps as mxlsp>
delete protocols mpls label-switched-path ${mxlsp.lsp.name}
</#list>


