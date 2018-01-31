package com.att.idp.foundation.configuration;

import java.io.IOException;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.kie.spring.KModuleBeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

// TODO: Auto-generated Javadoc
/**
 * The Class DroolsAutoConfiguration.
 */
@Configuration
@Profile(ApplicationConstants.APPLICATION_PROFILE_DROOLS)
public class DroolsAutoConfiguration {
    
    /** The Constant RULES_PATH. */
    private static final String RULES_PATH = "rules/";
    
    /**
     * Kie file system.
     *
     * @return the kie file system
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Bean
    @ConditionalOnMissingBean(KieFileSystem.class)
    public KieFileSystem kieFileSystem() throws IOException {
        final KieFileSystem kieFileSystem = getKieServices().newKieFileSystem();
        for (final Resource file : getRuleFiles()) {
            kieFileSystem.write(ResourceFactory.newClassPathResource(RULES_PATH + file.getFilename(), "UTF-8"));
        }        
        return kieFileSystem;
    }

    /**
     * Gets the rule files.
     *
     * @return the rule files
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private Resource[] getRuleFiles() throws IOException {
        final ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        return resourcePatternResolver.getResources("classpath*:" + RULES_PATH + "**/*.*");
    }
    
    /**
     * Kie container.
     *
     * @return the kie container
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Bean
    @ConditionalOnMissingBean(KieContainer.class)
    public KieContainer kieContainer() throws IOException {
        final KieRepository kieRepository = getKieServices().getRepository();
        
        kieRepository.addKieModule(new KieModule() {
        	/**
        	 * get Release ID
        	 */
            public ReleaseId getReleaseId() {
                return kieRepository.getDefaultReleaseId();
            }
        });
        
        final KieBuilder kieBuilder = getKieServices().newKieBuilder(kieFileSystem()); 
        kieBuilder.buildAll();
        
        return getKieServices().newKieContainer(kieRepository.getDefaultReleaseId());
    }
    
    /**
     * Gets the kie services.
     *
     * @return the kie services
     */
    private KieServices getKieServices() {
        return KieServices.Factory.get();
    }
    
    /**
     * Kie base.
     *
     * @return the kie base
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Bean
    @ConditionalOnMissingBean(KieBase.class)
    public KieBase kieBase() throws IOException {
        return kieContainer().getKieBase();
    }
    
    /**
     * Kie session.
     *
     * @return the kie session
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Bean
    @ConditionalOnMissingBean(KieSession.class)
    public KieSession kieSession() throws IOException {
        return kieContainer().newKieSession();
    }
    
    /**
     * Kie post processor.
     *
     * @return the k module bean factory post processor
     */
    /*
     *  As http://docs.jboss.org/drools/release/6.2.0.CR1/drools-docs/html/ch.kie.spring.html
     *  mentions: Without the org.kie.spring.KModuleBeanFactoryPostProcessor bean definition,
     *  the kie-spring integration will not work
     */
    @Bean
    @ConditionalOnMissingBean(KModuleBeanFactoryPostProcessor.class)
    public KModuleBeanFactoryPostProcessor kiePostProcessor() {
        return new KModuleBeanFactoryPostProcessor();
    }
}
