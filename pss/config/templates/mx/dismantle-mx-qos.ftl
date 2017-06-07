<#-- @ftlvariable name="qos_defs" type="java.util.List<net.es.oscars.dto.pss.params.mx.MxQos>" -->
<#list qos_defs as qos>
delete firewall family any filter ${qos.filterName}
<#if qos.apply>
delete firewall policer ${qos.policerName}
</#if>
</#list>
