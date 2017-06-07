<#-- @ftlvariable name="vpls" type="net.es.oscars.dto.pss.params.mx.MxVpls" -->

edit routing-instances ${vpls.serviceName}
set instance-type vpls
edit protocols vpls
set no-tunnel-services
set mtu 9100
edit site CE
<#list vpls.ifces as ifce>
set interface ${ifce}
</#list>

<#if vpls.loopback??>
top
edit interfaces lo0 unit 0 family inet
set address ${vpls.loopback}
</#if>


<#if vpls.statsFilter??>
top
edit firewall family vpls filter ${vpls.statsFilter}
set interface-specific
set term oscars then count oscars_counter
set term oscars then accept
</#if>
