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
                    url: 'https://github.com/SylvaineDidier/jenkins_java_maven_test.git',
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
            sh "mvn -B -DskipTests clean package"
         }
      }
      
      stage('Build'){
         steps{
            sh "mvn site:deploy"
         }
      }
      
      
	stage('Code Coverage') {
        steps {
            sh 'mvn cobertura:cobertura'
        }
        
        post {
	        always {
	            junit '**/test-reports/*.xml'
	            step([$class: 'CoberturaPublisher', autoUpdateHealth: true, autoUpdateStability: true, coberturaReportFile: '**/target/site/cobertura/*.xml', failUnhealthy: false, failUnstable: false, maxNumberOfBuilds: 2, onlyStable: false, sourceEncoding: 'ASCII', zoomCoverageChart: true])
	        }
	    }
    }
      stage ('Build and send Results Sonar') {
         steps {
            withSonarQubeEnv(installationName: 'My local Sonar', credentialsId: '1150527b-92b9-4ebe-a1e0-6f7adef21174') {
               sh 'mvn -B -DskipTests clean package sonar:sonar -Dsonar.login=$Login -Dsonar.password=$Password'
            }
         }
      }
      




   }
}
