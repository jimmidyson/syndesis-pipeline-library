#!/usr/bin/groovy

/**
 * Runs the system test suite.
 * @param parameters
 * @return
 */
def call(Map parameters = [:]) {

    def namespace = parameters.get('namespace', '')

    shareBinary('openshift', 'oc')

    container(name: 'maven') {
        git 'https://github.com/redhat-ipaas/ipaas-system-tests.git'
        def mavenOptions = namespace.isEmpty() ? "" : "-Dnamespace.use.existing=${namespace}"

        usingLocalBinaries {
            sh "mvn clean install -U ${mavenOptions}"
        }
    }
}
