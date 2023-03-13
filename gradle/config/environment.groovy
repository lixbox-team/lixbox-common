desc_project{
    type="library"
    version{
        majorVersion=7
        mediumVersion=1
        minorVersion=2
    }
    artefact{
        group="fr.lixbox.lixbox-common"
        project="lixbox-common"
        projectKey="${group}:${project}"
    }
}

pic{
    channel="lixbox"
	git{
	    uri="https://github.com/lixbox-team/lixbox-common.git"
	}    	
    jenkins{
        uri="https://ci.service.lixtec.fr/view/${channel}"
    }  
    sonar{
        uri="https://quality.service.lixtec.fr"
    }
    artifactory{
		uri="https://repos.service.lixtec.fr/artifactory"
    }
	mavencentral{
		uri="https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
	}
}

repository{
	artifactory{
	    release	="lixbox-release"
	    snapshot ="lixbox-snapshot"
	    libsRelease="libs-release"
	}
}