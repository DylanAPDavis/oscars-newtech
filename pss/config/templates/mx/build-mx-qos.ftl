<#-- @ftlvariable name="qos_defs" type="java.util.List<net.es.oscars.dto.pss.params.mx.MxQos>" -->
<#list qos_defs as qos>
edit firewall family any filter ${qos.filterName} term oscars then

<#if qos.apply>
set forwarding-class expedited-forwarding-vc
set loss-priority low
<#else>
set forwarding-class best-effort-vc
set loss-priority high
</#if>
set count oscars
set accept
top

<#if qos.apply>
edit firewall policer ${qos.policerName}
<#-- may need some massaging of values? -->
<#if qos.mbps gt 0>
    <#assign bw_limit = qos.mbps+"000000" >
    <#assign burst_limit = qos.mbps+"00000" >
set if-exceeding bandwidth-limit ${bw_limit}
set if-exceeding burst-size-limit ${burst_limit}
</#if>

<#if qos.policing == "SOFT">
set then loss-priority high
<#else>
set then discard
</#if>
top

set firewall family any filter ${qos.filterName} term oscars then policer ${qos.policerName}
</#if>

</#list>
