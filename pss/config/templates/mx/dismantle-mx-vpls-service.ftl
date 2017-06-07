<#-- @ftlvariable name="vpls" type="net.es.oscars.dto.pss.params.mx.MxVpls" -->


delete routing-instances ${vpls.serviceName}


<#if vpls.loopback??>
top
edit interfaces lo0 unit 0 family inet
delete address ${vpls.loopback}
</#if>

<#if vpls.statsFilter??>
top
delete firewall family vpls filter ${vpls.statsFilter}
</#if>
