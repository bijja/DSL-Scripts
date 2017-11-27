folder name('HPSIM'){
displayName('HPSIM-NEW')
    description('Folder for pHPSIM-NEW')
}

freeStyleJob('HPSIM/compile') {
    logRotator(-1, 10)
    
    scm {
        github('bijja/ims', 'master')
    }
    steps {
        maven ('clean compile')
  publishers {
  downstream ('HPSIM/test' , 'SUCCESS')
 // downstream 'next-job-name2' , 'SUCCESS'
 }
    }
    
}

mavenJob('HPSIM/test') {
    logRotator(-1, 10)
   
    scm {
        github('bijja/ims', 'master')
    }
  
    goals('clean test')
  
  publishers {
  downstream ('HPSIM/sonar' , 'SUCCESS')
 // downstream 'next-job-name2' , 'SUCCESS'
 }
}

mavenJob('HPSIM/sonar') {
    logRotator(-1, 10)
   
    scm {
        github('bijja/ims', 'master')
      
    }
  
    goals('clean sonar:sonar')
  publishers {
  downstream ('HPSIM/nexus' , 'SUCCESS')
 // downstream 'next-job-name2' , 'SUCCESS'
 }
}
mavenJob('HPSIM/nexus') {
    logRotator(-1, 10)
   
    scm {
        github('bijja/ims', 'master')
      
    }
  
    goals('clean deploy')
 
 }
buildPipelineView('HPSIM/Buildpipeline-new') {
    filterBuildQueue()
    filterExecutors()
    title('Project A CI Pipeline')
    displayedBuilds(5)
    selectedJob('HPSIM/compile')
    alwaysAllowManualTrigger()
    showPipelineParameters()
    refreshFrequency(60)
}
