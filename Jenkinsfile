pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                git url: 'https://github.com/Dianakaro12/qaautomated.git'
            }
        }

        stage('Run Tests') {
            steps {
                // Solo JSON para Xray y Allure
                bat 'mvn clean test -Dcucumber.plugin="pretty,json:target/cucumber-report.json"'
            }
        }

        stage('Verificar JSON') {
            steps {
                bat '''
                dir target
                if not exist target\\cucumber-report.json (
                    echo ERROR: El archivo cucumber-report.json no existe.
                    exit /b 1
                )
                for %%F in (target\\cucumber-report.json) do set size=%%~zF
                if %size% EQU 0 (
                    echo ERROR: El archivo cucumber-report.json está vacío.
                    exit /b 1
                )
                echo Archivo cucumber-report.json existe y no está vacío. Tamaño: %size% bytes
                '''
            }
        }

        stage('Verificar token parcial') {
            steps {
                withCredentials([string(credentialsId: 'github_pat', variable: 'XRAY_TOKEN')]) {
                    bat '''
                    echo Verificando los primeros caracteres del token...
                    echo Token empieza con: %XRAY_TOKEN:~0,10%
                    '''
                }
            }
        }

        stage('Allure Report') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    allure([
                        results: [[path: 'target/allure-results']],
                        reportBuildPolicy: 'ALWAYS'
                    ])
                }
            }
        }

        stage('Explicar errores con IA') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    bat 'java -cp target/classes;target/dependency/* utils.ExplainerMain'
                }
            }
        }

        stage('Upload Results to Xray') {
            steps {
                withCredentials([string(credentialsId: 'github_pat', variable: 'XRAY_TOKEN')]) {
                    bat '''
                    curl -H "Authorization: Bearer %XRAY_TOKEN%" ^
                         -H "Content-Type: application/json" ^
                         --data-binary @target/cucumber-report.json ^
                         https://xray.cloud.getxray.app/api/v2/import/execution/cucumber
                    '''
                }
            }
        }

        stage('Archive Reports') {
            steps {
                archiveArtifacts artifacts: 'target/cucumber-report.json', fingerprint: true
            }
        }

    }

    post {
        always {
            cucumber buildStatus: 'UNSTABLE', fileIncludePattern: '**/target/cucumber-report.json'
        }
    }
}
