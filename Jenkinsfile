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
                    url: $projetUrl,
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
      
      stage('SonarQube analysis') {
         environment {
            SCANNER_HOME = tool 'sonarqube'
         }
         steps {
               withSonarQubeEnv(installationName: 'Local SonarQube', credentialsId: 'sonarqube-token-for-jenkins') {
                     sh '''$SCANNER_HOME/bin/sonar-scanner \
                                          -Dsonar.login=$Login \
                                          -Dsonar.password=$Password \
                                          -Dsonar.projectKey=form-Maven \
                                          -Dsonar.projectName=form Maven\
                                          -Dsonar.sources=src/ \
                                          -Dsonar.java.binaries=target/classes/ \
                                          -Dsonar.exclusions=src/test/java/*/.java \
                                          -Dsonar.projectVersion=${BUILD_NUMBER}-${GIT_COMMIT_SHORT}'''
                  }
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
