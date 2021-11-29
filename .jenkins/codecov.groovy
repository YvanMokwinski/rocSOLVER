#!/usr/bin/env groovy
// This shared library is available at https://github.com/ROCmSoftwarePlatform/rocJENKINS/
@Library('rocJenkins@pong') _

// This is file for internal AMD use.
// If you are interested in running your own Jenkins, please raise a github issue for assistance.

import com.amd.project.*
import com.amd.docker.*
import java.nio.file.Path

def runCI =
{
    nodeDetails, jobName->

    def prj = new rocProject('rocSOLVER', 'CodeCov')
    // customize for project:
    // - debug mode (strongly recommended for reliable results but not required).
    prj.defaults.ccache = true
    prj.timeout.compile = 680
    prj.timeout.test = 360
    prj.paths.build_command = './install.sh -cg --codecoverage --cmake-arg -DWERROR=ON'


    // Define test architectures, optional rocm version argument is available
    def nodes = new dockerNodes(nodeDetails, jobName, prj)

    boolean formatCheck = false

    def compileCommand =
    {
        platform, project->

        commonGroovy = load "${project.paths.project_src_prefix}/.jenkins/common.groovy"
        commonGroovy.runCompileCommand(platform, project, jobName)
    }

    def testCommand =
    {
        platform, project->
	// Need to target a category
	def gfilter = '*checkin_lapack*'
        commonGroovy.runCoverageCommand(platform, project, gfilter, "debug")
    }

    buildProject(prj, formatCheck, nodes.dockerArray, compileCommand, testCommand, null)

}

ci: {
    String urlJobName = auxiliary.getTopJobName(env.BUILD_URL)

    def propertyList = ["compute-rocm-dkms-no-npi":[pipelineTriggers([cron('0 1 * * 6')])],
                        "compute-rocm-dkms-no-npi-hipclang":[pipelineTriggers([cron('0 1 * * 6')])],
                        "rocm-docker":[]]
    propertyList = auxiliary.appendPropertyList(propertyList)

    def jobNameList = ["compute-rocm-dkms-no-npi":([ubuntu18:['gfx900'],centos7:['gfx906'],sles15sp1:['gfx906']]),
                       "compute-rocm-dkms-no-npi-hipclang":([ubuntu18:['gfx900'],centos7:['gfx906'],centos8:['gfx906'],sles15sp1:['gfx908']]),
                       "rocm-docker":([ubuntu18:['gfx900']])]
    jobNameList = auxiliary.appendJobNameList(jobNameList, 'rocBLAS')

    propertyList.each
    {
        jobName, property->
        if (urlJobName == jobName)
            properties(auxiliary.addCommonProperties(property))
    }

    jobNameList.each
    {
        jobName, nodeDetails->
        if (urlJobName == jobName)
            stage(jobName) {
                runCI(nodeDetails, jobName)
            }
    }

    // For url job names that are not listed by the jobNameList i.e. compute-rocm-dkms-no-npi-1901
    if(!jobNameList.keySet().contains(urlJobName))
    {
        properties(auxiliary.addCommonProperties([pipelineTriggers([cron('0 1 * * *')])]))
        stage(urlJobName) {
            runCI([ubuntu18:['gfx900', 'gfx906']], urlJobName)
        }
    }
}
