package java.io;

import sun.misc.ObjectInputFilter;

// [Y4-00008]
@SuppressWarnings("all")
public class DeserializationFilter implements ObjectInputFilter {
    private final int maxLength;
    private final int maxBytes;
    private final int maxDepth;
    private final int maxRefs;

    private static final String[] BLACK_LIST = new String[]{
            // https://github.com/mogwailabs/deserialization-filter-blacklists
            "bsh.XThis",
            "bsh.Interpreter",
            "com.mchange.v2.c3p0.PoolBackedDataSource",
            "com.mchange.v2.c3p0.impl.PoolBackedDataSourceBase",
            "clojure.lang.PersistentArrayMap",
            "clojure.inspector.proxy$javax.swing.table.AbstractTableModel$ff19274a",
            "org.apache.commons.beanutils.BeanComparator",
            "org.apache.commons.collections.Transformer",
            "org.apache.commons.collections.functors.ChainedTransformer",
            "org.apache.commons.collections.functors.ConstantTransformer",
            "org.apache.commons.collections.functors.InstantiateTransformer",
            "org.apache.commons.collections.map.LazyMap",
            "org.apache.commons.collections.functors.InvokerTransformer",
            "org.apache.commons.collections.keyvalue.TiedMapEntry",
            "org.apache.commons.collections4.comparators.TransformingComparator",
            "org.apache.commons.collections4.functors.InvokerTransformer",
            "org.apache.commons.collections4.functors.ChainedTransformer",
            "org.apache.commons.collections4.functors.ConstantTransformer",
            "org.apache.commons.collections4.functors.InstantiateTransformer",
            "org.apache.commons.fileupload.disk.DiskFileItem",
            "org.apache.commons.io.output.DeferredFileOutputStream",
            "org.apache.commons.io.output.ThresholdingOutputStream",
            "org.apache.wicket.util.upload.DiskFileItem",
            "org.apache.wicket.util.io.DeferredFileOutputStream",
            "org.apache.wicket.util.io.ThresholdingOutputStream",
            "org.codehaus.groovy.runtime.ConvertedClosure",
            "org.codehaus.groovy.runtime.MethodClosure",
            "org.hibernate.engine.spi.TypedValue",
            "org.hibernate.tuple.component.AbstractComponentTuplizer",
            "org.hibernate.tuple.component.PojoComponentTuplizer",
            "org.hibernate.type.AbstractType",
            "org.hibernate.type.ComponentType",
            "org.hibernate.type.Type",
            "org.hibernate.EntityMode",
            "com.sun.rowset.JdbcRowSetImpl",
            "org.jboss.interceptor.builder.InterceptionModelBuilder",
            "org.jboss.interceptor.builder.MethodReference",
            "org.jboss.interceptor.proxy.DefaultInvocationContextFactory",
            "org.jboss.interceptor.proxy.InterceptorMethodHandler",
            "org.jboss.interceptor.reader.ClassMetadataInterceptorReference",
            "org.jboss.interceptor.reader.DefaultMethodMetadata",
            "org.jboss.interceptor.reader.ReflectiveClassMetadata",
            "org.jboss.interceptor.reader.SimpleInterceptorMetadata",
            "org.jboss.interceptor.spi.instance.InterceptorInstantiator",
            "org.jboss.interceptor.spi.metadata.InterceptorReference",
            "org.jboss.interceptor.spi.metadata.MethodMetadata",
            "org.jboss.interceptor.spi.model.InterceptionType",
            "org.jboss.interceptor.spi.model.InterceptionModel",
            "sun.rmi.server.UnicastRef",
            "sun.rmi.transport.LiveRef",
            "sun.rmi.transport.tcp.TCPEndpoint",
            "java.rmi.server.RemoteObject",
            "java.rmi.server.RemoteRef",
            "java.rmi.server.UnicastRemoteObject",
            "sun.rmi.server.ActivationGroupImpl",
            "sun.rmi.server.UnicastServerRef",
            "org.springframework.aop.framework.AdvisedSupport",
            "net.sf.json.JSONObject",
            "org.jboss.weld.interceptor.builder.InterceptionModelBuilder",
            "org.jboss.weld.interceptor.builder.MethodReference",
            "org.jboss.weld.interceptor.proxy.DefaultInvocationContextFactory",
            "org.jboss.weld.interceptor.proxy.InterceptorMethodHandler",
            "org.jboss.weld.interceptor.reader.ClassMetadataInterceptorReference",
            "org.jboss.weld.interceptor.reader.DefaultMethodMetadata",
            "org.jboss.weld.interceptor.reader.ReflectiveClassMetadata",
            "org.jboss.weld.interceptor.reader.SimpleInterceptorMetadata",
            "org.jboss.weld.interceptor.spi.instance.InterceptorInstantiator",
            "org.jboss.weld.interceptor.spi.metadata.InterceptorReference",
            "org.jboss.weld.interceptor.spi.metadata.MethodMetadata",
            "org.jboss.weld.interceptor.spi.model.InterceptionModel",
            "org.jboss.weld.interceptor.spi.model.InterceptionType",
            "org.python.core.PyObject",
            "org.python.core.PyBytecode",
            "org.python.core.PyFunction",
            "org.mozilla.javascript.**",
            "org.apache.myfaces.context.servlet.FacesContextImpl",
            "org.apache.myfaces.context.servlet.FacesContextImplBase",
            "org.apache.myfaces.el.CompositeELResolver",
            "org.apache.myfaces.el.unified.FacesELContext",
            "org.apache.myfaces.view.facelets.el.ValueExpressionMethodExpression",
            "com.sun.syndication.feed.impl.ObjectBean",
            "org.springframework.beans.factory.ObjectFactory",
            "org.springframework.aop.framework.AdvisedSupport",
            "org.springframework.aop.target.SingletonTargetSource",
            "com.vaadin.data.util.NestedMethodProperty",
            "com.vaadin.data.util.PropertysetItem",

            // CUSTOM
            "com.alibaba.fastjson.JSONArray",
            "com.alibaba.fastjson2.JSONArray",
            "com.fasterxml.jackson.databind.node.POJONode",
            "br.com.anteros.dbcp.AnterosDBCPConfig",
            "br.com.anteros.dbcp.AnterosDBCPDataSource",
            "bsh.Interpreter",
            "bsh.XThis",
            "ch.qos.logback.core.db.DriverManagerConnectionSource",
            "ch.qos.logback.core.db.JNDIConnectionSource",
            "clojure.inspector.proxy$javax.swing.table.AbstractTableModel$ff19274a",
            "clojure.lang.PersistentArrayMap",
            "com.alibaba.fastjson.TypeReference",
            "com.caucho.config.types.ResourceRef",
            "com.ibatis.sqlmap.engine.transaction.jta.JtaTransactionConfig",
            "com.mchange.v2.c3p0.ComboPooledDataSource",
            "com.mchange.v2.c3p0.debug.AfterCloseLoggingComboPooledDataSource",
            "com.mchange.v2.c3p0.impl.PoolBackedDataSourceBase",
            "com.mchange.v2.c3p0.JndiRefForwardingDataSource",
            "com.mchange.v2.c3p0.PoolBackedDataSource",
            "com.mysql.cj.jdbc.admin.MiniAdmin",
            "com.mysql.cj.jdbc.MysqlConnectionPoolDataSource",
            "com.mysql.cj.jdbc.MysqlXADataSource",
            "com.newrelic.agent.deps.ch.qos.logback.core.db.DriverManagerConnectionSource",
            "com.newrelic.agent.deps.ch.qos.logback.core.db.JNDIConnectionSource",
            "com.nqadmin.rowset.JdbcRowSetImpl",
            "com.oracle.wls.shaded.org.apache.xalan.lib.sql.JNDIConnectionPool",
            "com.p6spy.engine.spy.P6DataSource",
            "com.pastdev.httpcomponents.configuration.JndiConfiguration",
            "com.sun.deploy.security.ruleset.DRSHelper",
            "com.sun.jmx.interceptor.DefaultMBeanServerInterceptor",
            "com.sun.jmx.mbeanserver.JmxMBeanServer",
            "com.sun.jmx.mbeanserver.NamedObject",
            "com.sun.jmx.mbeanserver.Repository",
            "com.sun.org.apache.bcel.internal.util.ClassLoader",
            "com.sun.org.apache.xalan.internal.lib.sql.JNDIConnectionPool",
            "com.sun.org.apache.xalan.internal.xslt.ObjectFactory",
            "com.sun.org.apache.xalan.internal.xslt.Process",
            "com.sun.org.apache.xalan.internal.xsltc.DOM",
            "com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet",
            "com.sun.org.apache.xalan.internal.xsltc.TransletException",
            "com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl",
            "com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl",
            "com.sun.org.apache.xml.internal.dtm.DTMAxisIterator",
            "com.sun.org.apache.xml.internal.serializer.SerializationHandler",
            "com.sun.rowset.JdbcRowSetImpl",
            "com.sun.syndication.feed.impl.ObjectBean",
            "com.vaadin.data.Property",
            "com.vaadin.data.util.NestedMethodProperty",
            "com.vaadin.data.util.PropertysetItem",
            "com.zaxxer.hikari.HikariConfig",
            "com.zaxxer.hikari.HikariDataSource",
            "flex.messaging.util.concurrent.AsynchBeansWorkManagerExecutor",
            "groovy.lang.Closure",
            "java.beans.EventHandler",
            "java.lang.reflect.Proxy",
            "java.net.Inet4Address",
            "java.net.Inet6Address",
            "java.net.InetAddress",
            "java.net.InetSocketAddress",
            "java.net.Socket",
            "java.net.URL",
            "java.net.URLStreamHandler",
            "java.rmi.registry.Registry",
            "java.rmi.RemoteObjectInvocationHandler",
            "java.rmi.server.ObjID",
            "java.rmi.server.RemoteObject",
            "java.rmi.server.RemoteRef",
            "java.rmi.server.UnicastRemoteObject",
            "java.util.Base64",
            "java.util.Comparator",
            "java.util.logging.FileHandler",
            "java.util.PriorityQueue",
            "javax.el.ELContext",
            "javax.faces.context.FacesContext",
            "javax.management.BadAttributeValueExpException",
            "javax.management.DynamicMBean",
            "javax.management.MBeanServer",
            "javax.management.MBeanServerInvocationHandler",
            "javax.management.ObjectName",
            "javax.management.openmbean.CompositeData",
            "javax.management.openmbean.CompositeDataInvocationHandler",
            "javax.management.openmbean.CompositeType",
            "javax.management.openmbean.OpenDataException",
            "javax.management.openmbean.OpenType",
            "javax.management.openmbean.SimpleType",
            "javax.management.openmbean.TabularDataSupport",
            "javax.management.openmbean.TabularType",
            "javax.net.SocketFactory",
            "javax.servlet.http.HttpSession",
            "javax.servlet.ServletContext",
            "javax.servlet.ServletRequestEvent",
            "javax.servlet.ServletRequestListener",
            "javax.swing.JEditorPane",
            "javax.swing.JTextPane",
            "javax.xml.transform.Templates",
            "jodd.db.connection.DataSourceConnectionProvider",
            "net.sf.ehcache.hibernate.EhcacheJtaTransactionManagerLookup",
            "net.sf.ehcache.transaction.manager.DefaultTransactionManagerLookup",
            "net.sf.ehcache.transaction.manager.selector.GenericJndiSelector",
            "net.sf.ehcache.transaction.manager.selector.GlassfishSelector",
            "net.sf.json.JSONObject",
            "oadd.org.apache.commons.dbcp.cpdsadapter.DriverAdapterCPDS",
            "oadd.org.apache.commons.dbcp.datasources.PerUserPoolDataSource",
            "oadd.org.apache.commons.dbcp.datasources.SharedPoolDataSource",
            "oadd.org.apache.xalan.lib.sql.JNDIConnectionPool",
            "om.mchange.v2.c3p0.WrapperConnectionPoolDataSource",
            "om.sun.corba.se.spi.orbutil.proxy.CompositeInvocationHandlerImpl",
            "oracle.jdbc.connector.OracleManagedConnectionFactory",
            "oracle.jdbc.pool.OraclePooledConnection",
            "oracle.jdbc.rowset.OracleJDBCRowSet",
            "oracle.jms.AQjmsQueueConnectionFactory",
            "oracle.jms.AQjmsTopicConnectionFactory",
            "oracle.jms.AQjmsXAConnectionFactory",
            "oracle.jms.AQjmsXAQueueConnectionFactory",
            "oracle.jms.AQjmsXATopicConnectionFactory",
            "org..springframework.transaction.jta.JtaTransactionManager",
            "org.aoju.bus.proxy.provider.remoting.RmiProvider",
            "org.aoju.bus.proxy.provider.RmiProvider",
            "org.apache.activemq.ActiveMQConnectionFactory",
            "org.apache.activemq.ActiveMQXAConnectionFactory",
            "org.apache.activemq.jms.pool.JcaPooledConnectionFactory",
            "org.apache.activemq.jms.pool.XaPooledConnectionFactory",
            "org.apache.activemq.pool.JcaPooledConnectionFactory",
            "org.apache.activemq.pool.PooledConnectionFactory",
            "org.apache.activemq.pool.XaPooledConnectionFactory",
            "org.apache.activemq.spring.ActiveMQConnectionFactory",
            "org.apache.activemq.spring.ActiveMQXAConnectionFactory",
            "org.apache.aries.transaction.jms.internal.XaPooledConnectionFactory",
            "org.apache.aries.transaction.jms.RecoverablePooledConnectionFactory",
            "org.apache.axis2.jaxws.spi.handler.HandlerResolverImpl",
            "org.apache.axis2.transport.jms.JMSOutTransportInfo",
            "org.apache.bcel.internal.util.ClassLoader",
            "org.apache.catalina.authenticator.AuthenticatorBase",
            "org.apache.catalina.connector.Request",
            "org.apache.catalina.connector.RequestFacade",
            "org.apache.catalina.connector.Response",
            "org.apache.catalina.core.ApplicationFilterConfig",
            "org.apache.catalina.core.ApplicationServletRegistration",
            "org.apache.catalina.core.StandardContext",
            "org.apache.catalina.core.StandardService",
            "org.apache.catalina.core.StandardWrapperValue.invoke",
            "org.apache.catalina.deploy.FilterDef",
            "org.apache.catalina.deploy.FilterMap",
            "org.apache.catalina.loader.ParallelWebappClassLoader",
            "org.apache.catalina.loader.WebappClassLoaderBase",
            "org.apache.click.control.Column",
            "org.apache.click.control.Column$ColumnComparator",
            "org.apache.click.control.Table",
            "org.apache.commons.beanutils.BeanComparator",
            "org.apache.commons.codec.binary.Base64",
            "org.apache.commons.collections.comparators.TransformingComparator",
            "org.apache.commons.collections.functors.ChainedTransformer",
            "org.apache.commons.collections.functors.ConstantTransformer",
            "org.apache.commons.collections.functors.InstantiateTransformer",
            "org.apache.commons.collections.functors.InvokerTransformer",
            "org.apache.commons.collections.functors.MapTransformer",
            "org.apache.commons.collections.keyvalue.TiedMapEntry",
            "org.apache.commons.collections.map.LazyMap",
            "org.apache.commons.collections.map.TransformedMap",
            "org.apache.commons.collections.Transformer",
            "org.apache.commons.collections4.comparators.TransformingComparator",
            "org.apache.commons.collections4.functors.ChainedTransformer",
            "org.apache.commons.collections4.functors.ConstantTransformer",
            "org.apache.commons.collections4.functors.InstantiateTransformer",
            "org.apache.commons.collections4.functors.InvokerTransformer",
            "org.apache.commons.collections4.functors.MapTransformer",
            "org.apache.commons.collections4.keyvalue.TiedMapEntry",
            "org.apache.commons.collections4.map.LazyMap",
            "org.apache.commons.collections4.map.TransformedMap",
            "org.apache.commons.collections4.Transformer",
            "org.apache.commons.configuration.JNDIConfiguration",
            "org.apache.commons.configuration2.JNDIConfiguration",
            "org.apache.commons.dbcp.cpdsadapter.DriverAdapterCPDS",
            "org.apache.commons.dbcp.datasources.PerUserPoolDataSource",
            "org.apache.commons.dbcp.datasources.SharedPoolDataSource",
            "org.apache.commons.dbcp2.cpdsadapter.DriverAdapterCPDS",
            "org.apache.commons.dbcp2.datasources.PerUserPoolDataSource",
            "org.apache.commons.dbcp2.datasources.SharedPoolDataSource",
            "org.apache.commons.fileupload.disk.DiskFileItem",
            "org.apache.commons.io.FileUtils",
            "org.apache.commons.io.output.DeferredFileOutputStream",
            "org.apache.commons.io.output.ThresholdingOutputStream",
            "org.apache.commons.jelly.impl.Embedded",
            "org.apache.commons.proxy.provider.remoting.RmiProvider",
            "org.apache.coyote.AbstractProtocol;",
            "org.apache.coyote.ProtocolHandler",
            "org.apache.coyote.Request",
            "org.apache.coyote.Response",
            "org.apache.cxf.jaxrs.provider.XSLTJaxbProvider",
            "org.apache.hadoop.shaded.com.zaxxer.hikari.HikariConfig",
            "org.apache.ibatis.datasource.jndi.JndiDataSourceFactory",
            "org.apache.ibatis.parsing.XPathParser",
            "org.apache.ignite.cache.jta.jndi.CacheJndiTmFactory",
            "org.apache.ignite.cache.jta.jndi.CacheJndiTmLookup",
            "org.apache.log4j.receivers.db.DriverManagerConnectionSource",
            "org.apache.log4j.receivers.db.JNDIConnectionSource",
            "org.apache.myfaces.context.servlet.FacesContextImpl",
            "org.apache.myfaces.context.servlet.FacesContextImplBase",
            "org.apache.myfaces.el.CompositeELResolver",
            "org.apache.myfaces.el.unified.FacesELContext",
            "org.apache.myfaces.view.facelets.el.ValueExpressionMethodExpression",
            "org.apache.openjpa.ee.JNDIManagedRuntime",
            "org.apache.openjpa.ee.RegistryManagedRuntime",
            "org.apache.openjpa.ee.WASRegistryManagedRuntime",
            "org.apache.shiro.codec.Base64",
            "org.apache.shiro.codec.CodecSupport",
            "org.apache.shiro.crypto.AesCipherService",
            "org.apache.shiro.io.DefaultSerializer",
            "org.apache.shiro.jndi.JndiObjectFactory",
            "org.apache.shiro.realm.jndi.JndiRealmFactory",
            "org.apache.shiro.util.ByteSource",
            "org.apache.tomcat.dbcp.dbcp.cpdsadapter.DriverAdapterCPDS",
            "org.apache.tomcat.dbcp.dbcp.datasources.PerUserPoolDataSource",
            "org.apache.tomcat.dbcp.dbcp.datasources.SharedPoolDataSource",
            "org.apache.tomcat.dbcp.dbcp2.BasicDataSourc",
            "org.apache.tomcat.dbcp.dbcp2.cpdsadapter.DriverAdapterCPDS",
            "org.apache.tomcat.dbcp.dbcp2.datasources.PerUserPoolDataSource",
            "org.apache.tomcat.dbcp.dbcp2.datasources.SharedPoolDataSource",
            "org.apache.tomcat.util.buf.ByteChunk",
            "org.apache.tomcat.util.descriptor.web.FilterDef",
            "org.apache.tomcat.util.descriptor.web.FilterMap",
            "org.apache.tomcat.util.modeler.BaseModelMBean",
            "org.apache.tomcat.util.modeler.Registry",
            "org.apache.wicket.util.file.Files",
            "org.apache.wicket.util.io.DeferredFileOutputStream",
            "org.apache.wicket.util.io.ThresholdingOutputStream",
            "org.apache.wicket.util.upload.DiskFileItem",
            "org.apache.xalan.lib.sql.JNDIConnectionPool",
            "org.apache.xalan.xslt.ObjectFactory",
            "org.apache.xalan.xslt.Process",
            "org.apache.xalan.xsltc.DOM",
            "org.apache.xalan.xsltc.runtime.AbstractTranslet",
            "org.apache.xalan.xsltc.TransletException",
            "org.apache.xalan.xsltc.trax.TemplatesImpl",
            "org.apache.xalan.xsltc.trax.TransformerFactoryImpl",
            "org.apache.xbean.propertyeditor.JndiConverter",
            "org.apache.xml.dtm.DTMAxisIterator",
            "org.apache.xml.serializer.SerializationHandler",
            "org.arrah.framework.rdbms.UpdatableJdbcRowsetImpl",
            "org.aspectj.weaver.tools.cache.SimpleCache$StoreableCachingMap",
            "org.codehaus.groovy.runtime.ConvertedClosure",
            "org.codehaus.groovy.runtime.MethodClosure",
            "org.docx4j.org.apache.xalan.lib.sql.JNDIConnectionPool",
            "org.hibernate.engine.spi.TypedValue",
            "org.hibernate.engine.spi.TypedValue$1",
            "org.hibernate.engine.TypedValue",
            "org.hibernate.EntityMode",
            "org.hibernate.jmx.StatisticsService",
            "org.hibernate.property.access.spi.Getter",
            "org.hibernate.property.access.spi.GetterMethodImpl",
            "org.hibernate.property.BasicPropertyAccessor$BasicGetter",
            "org.hibernate.property.Getter",
            "org.hibernate.tuple.component.AbstractComponentTuplizer",
            "org.hibernate.tuple.component.PojoComponentTuplizer",
            "org.hibernate.tuple.entity.EntityEntityModeToTuplizerMapping",
            "org.hibernate.tuple.EntityModeToTuplizerMapping",
            "org.hibernate.type.AbstractType",
            "org.hibernate.type.ComponentType",
            "org.hibernate.type.Type",
            "org.jboss.interceptor.builder.InterceptionModelBuilder",
            "org.jboss.interceptor.builder.MethodReference",
            "org.jboss.interceptor.proxy.DefaultInvocationContextFactory",
            "org.jboss.interceptor.proxy.InterceptorMethodHandler",
            "org.jboss.interceptor.reader.ClassMetadataInterceptorReference",
            "org.jboss.interceptor.reader.DefaultMethodMetadata",
            "org.jboss.interceptor.reader.ReflectiveClassMetadata",
            "org.jboss.interceptor.reader.SimpleInterceptorMetadata",
            "org.jboss.interceptor.spi.context.InvocationContextFactory",
            "org.jboss.interceptor.spi.instance.InterceptorInstantiator",
            "org.jboss.interceptor.spi.metadata.ClassMetadata",
            "org.jboss.interceptor.spi.metadata.InterceptorReference",
            "org.jboss.interceptor.spi.metadata.MethodMetadata",
            "org.jboss.interceptor.spi.model.InterceptionModel",
            "org.jboss.interceptor.spi.model.InterceptionType",
            "org.jboss.remoting3.Channel",
            "org.jboss.remoting3.Connection",
            "org.jboss.remoting3.Endpoint",
            "org.jboss.remoting3.OpenListener",
            "org.jboss.remoting3.remote.HttpUpgradeConnectionProviderFactory",
            "org.jboss.remoting3.Remoting",
            "org.jboss.remoting3.spi.ConnectionHandler",
            "org.jboss.remoting3.spi.ConnectionHandlerContext",
            "org.jboss.remoting3.spi.ConnectionHandlerFactory",
            "org.jboss.util.propertyeditor.DocumentEditor",
            "org.jboss.weld.interceptor.builder.InterceptionModelBuilder",
            "org.jboss.weld.interceptor.builder.MethodReference",
            "org.jboss.weld.interceptor.proxy.DefaultInvocationContextFactory",
            "org.jboss.weld.interceptor.proxy.InterceptorMethodHandler",
            "org.jboss.weld.interceptor.reader.ClassMetadataInterceptorReference",
            "org.jboss.weld.interceptor.reader.DefaultMethodMetadata",
            "org.jboss.weld.interceptor.reader.ReflectiveClassMetadata",
            "org.jboss.weld.interceptor.reader.SimpleInterceptorMetadata",
            "org.jboss.weld.interceptor.spi.context.InvocationContextFactory",
            "org.jboss.weld.interceptor.spi.instance.InterceptorInstantiator",
            "org.jboss.weld.interceptor.spi.metadata.ClassMetadata",
            "org.jboss.weld.interceptor.spi.metadata.InterceptorReference",
            "org.jboss.weld.interceptor.spi.metadata.MethodMetadata",
            "org.jboss.weld.interceptor.spi.model.InterceptionModel",
            "org.jboss.weld.interceptor.spi.model.InterceptionType",
            "org.jdom.Document",
            "org.jdom.Element",
            "org.jdom.input.SAXBuilder",
            "org.jdom.transform.XSLTransformer",
            "org.jdom2.transform.XSLTransformer",
            "org.jsecurity.realm.jndi.JndiRealmFactory",
            "org.mozilla.javascript.Callable",
            "org.mozilla.javascript.ClassCache",
            "org.mozilla.javascript.Context",
            "org.mozilla.javascript.IdScriptableObject",
            "org.mozilla.javascript.MemberBox",
            "org.mozilla.javascript.NativeError",
            "org.mozilla.javascript.NativeJavaArray",
            "org.mozilla.javascript.NativeJavaMethod",
            "org.mozilla.javascript.NativeJavaObject",
            "org.mozilla.javascript.NativeObject",
            "org.mozilla.javascript.Scriptable",
            "org.mozilla.javascript.ScriptableObject",
            "org.mozilla.javascript.tools.shell.Environment",
            "org.python.core.PyBytecode",
            "org.python.core.PyFunction",
            "org.python.core.PyObject",
            "org.python.core.PyString",
            "org.python.core.PyStringMap",
            "org.quartz.utils.JNDIConnectionProvider",
            "org.reflections.Reflections",
            "org.slf4j.ext.EventData",
            "org.springframework.aop.config.MethodLocatingFactoryBean",
            "org.springframework.aop.framework.AdvisedSupport",
            "org.springframework.aop.framework.JdkDynamicAopProxy",
            "org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor",
            "org.springframework.aop.target.SingletonTargetSource",
            "org.springframework.aop.TargetSource",
            "org.springframework.beans.factory.config.BeanReferenceFactoryBean",
            "org.springframework.beans.factory.config.PropertyPathFactoryBean",
            "org.springframework.beans.factory.ObjectFactory",
            "org.springframework.beans.factory.support.AutowireUtils$ObjectFactoryDelegatingInvocationHandler",
            "org.springframework.core.SerializableTypeWrapper.$MethodInvokeTypeProvider",
            "org.springframework.web.servlet.handler.HandlerInterceptorAdapter",
            "org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping",
            "org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping",
            "org.springframework.web.servlet.support.RequestContextUtils",
            "org.xnio.FutureResult",
            "org.xnio.IoFuture",
            "org.xnio.OptionMap",
            "org.xnio.Options",
            "org.xnio.Result",
            "org.xnio.ssl.JsseXnioSsl",
            "org.xnio.Xnio",
            "org.xnio.XnioWorker",
            "sun.misc.BASE64Decoder",
            "sun.misc.BASE64Encoder",
            "sun.reflect.annotation.AnnotationInvocationHandler",
            "sun.rmi.server.ActivationGroupImpl",
            "sun.rmi.server.UnicastRef",
            "sun.rmi.server.UnicastServerRef",
            "sun.rmi.transport.LiveRef",
            "sun.rmi.transport.tcp.TCPEndpoint"
    };

    public DeserializationFilter(int maxLength, int maxDepth, int maxRefs, int maxBytes) {
        this.maxLength = maxLength;
        this.maxDepth = maxDepth;
        this.maxRefs = maxRefs;
        this.maxBytes = maxBytes;
    }

    @Override
    public Status checkInput(FilterInfo filterInfo) {
        if (filterInfo.depth() > maxDepth) {
            return Status.REJECTED;
        }
        if (filterInfo.references() > maxRefs) {
            return Status.REJECTED;
        }
        if (filterInfo.arrayLength() > maxLength) {
            return Status.REJECTED;
        }
        if (filterInfo.streamBytes() > maxBytes) {
            return Status.REJECTED;
        }
        for (String s : BLACK_LIST) {
            if (filterInfo.serialClass().getName().equals(s)) {
                return Status.REJECTED;
            }
        }
        return Status.ALLOWED;
    }
}