pipeline {

   agent any

   tools{
      maven 'mon_maven_auto'
   }

   stages {
      stage('Clone') {
         steps {
            checkout([$class: 'GitSCM',
                branches: [[name: '*/master' ]],
                extensions: scm.extensions,
                userRemoteConfigs: [[
                    url: 'https://github.com/SylvaineDidier/prrojet-for-training.git',
                    credentialsId: '86ebefd1-279b-46f1-be3a-ca3094b4750d'
                ]]
            ])

            //List all directories
            sh "ls -lart ./*"
         }
      }
	  stage('Compile'){
         steps{
            withMaven(maven:'mon_maven_auto')
            {
              sh "mvn compile"
            }
         }
      }
      stage('Test'){
         steps{
            withMaven(maven:'mon_maven_auto')
            {
              sh "mvn test"
            }
         }
         
      }
      
      stage('Build'){
         steps{
            sh "mvn -B -DskipTests clean install"
         }
      }
      
       stage('SonarQube Analysis') {
		    def mvn = tool 'mon_maven_auto';
		    withSonarQubeEnv(installationName: 'Local SonarQube', credentialsId: 'sonarqube-token-for-jenkins') {
		      sh "${mvn}/bin/mvn clean verify sonar:sonar -Dsonar.projectKey=form-Maven"
		    }
		  }
      
	stage('Code Coverage') {
        steps {
            sh 'mvn clean cobertura:cobertura'
        }
        
        post {
	        always {
	            step([$class: 'CoberturaPublisher', autoUpdateHealth: true, autoUpdateStability: true, coberturaReportFile: '**/target/site/cobertura/*.xml', failUnhealthy: false, failUnstable: false, maxNumberOfBuilds: 2, onlyStable: false, sourceEncoding: 'ASCII', zoomCoverageChart: true])
	        }
	    }
    }
    

      




   }
}
