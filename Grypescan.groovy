def checkForVulnerabilities(String imageName) {
    def grypeScanner = Jenkins.instance.getExtensionList('io.jenkins.plugins.grypescanner.GrypeScanner')[0]
    def scanResult = grypeScanner.scan(imageName)
    
    if (scanResult.hasVulnerabilities()) {
        println "Vulnerabilities found in image: ${imageName}"
        scanResult.getVulnerabilities().each { vulnerability ->
            println "ID: ${vulnerability.getId()}, Severity: ${vulnerability.getSeverity()}"
        }
    } else {
        println "No vulnerabilities found in image: ${imageName}"
    }
