<#-- @ftlvariable name="ifces" type="java.util.List<net.es.oscars.dto.pss.params.mx.MxIfce>" -->

<#list ifces as ifce>
delete interfaces ${ifce.port} unit ${ifce.vlan}
</#list>

