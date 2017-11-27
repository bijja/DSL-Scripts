freeStyleJob('project-hpsim/compile') {
    logRotator(-1, 10)
    
    scm {
        github('bijja/ims', 'master')
    }
    steps {
        maven ('clean compile')
  publishers {
  downstream ('project-hpsim/test' , 'SUCCESS')
 // downstream 'next-job-name2' , 'SUCCESS'
 }
    }
    
}

mavenJob('project-hpsim/test') {
    logRotator(-1, 10)
   
    scm {
        github('bijja/ims', 'master')
    }
  
    goals('clean test')
  
  publishers {
  downstream ('project-hpsim/sonar' , 'SUCCESS')
 // downstream 'next-job-name2' , 'SUCCESS'
 }
}

mavenJob('project-hpsim/sonar') {
    logRotator(-1, 10)
   
    scm {
        github('bijja/ims', 'master')
      
    }
  
    goals('clean sonar:sonar')
  publishers {
  downstream ('project-hpsim/nexus' , 'SUCCESS')
 // downstream 'next-job-name2' , 'SUCCESS'
 }
}
mavenJob('project-hpsim/nexus') {
    logRotator(-1, 10)
   
    scm {
        github('bijja/ims', 'master')
      
    }
  
    goals('clean deploy')
 
 }
buildPipelineView('project-hpsim/Buildpipeline') {
    filterBuildQueue()
    filterExecutors()
    title('Project A CI Pipeline')
    displayedBuilds(5)
    selectedJob('project-hpsim/compile')
    alwaysAllowManualTrigger()
    showPipelineParameters()
    refreshFrequency(60)
}