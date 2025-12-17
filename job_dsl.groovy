folder('Tools') {
    description('Folder for miscellaneous tools.')
}

job('Tools/clone-repository') {
    description('Clone a Git rep')
    parameters {
        stringParam('GIT_REPOSITORY_URL', '', 'Git URL of the repository to clone')
    }
    steps {
        shell('git clone $GIT_REPOSITORY_URL')
    }
    wrappers {
        preBuildCleanup()
    }
    triggers {}
}

job('Tools/SEED') {
    description('job creat')
    parameters {
        stringParam('GITHUB_NAME', '', 'GitHub repository owner/repo_name (e.g.: "EpitechIT31000/chocolatine")')
        stringParam('DISPLAY_NAME', '', 'Display name for the job')
    }
    steps {
        dsl {
            text("""
                job("\${DISPLAY_NAME}") { 
                    description('seed creat job')
                    properties {
                    	githubProjectUrl("https://github.com/\${GITHUB_NAME}")
                    }
                    scm {
                        git {
                            remote {
                                url("https://github.com/\${GITHUB_NAME}.git")
                            }
                            branch('*/main')
                        }
                    }
                    triggers {
                        scm('* * * * *')
                    }
                    wrappers {
                        preBuildCleanup()
                    }
                    steps {
                        shell('make fclean')
                        shell('make')
                        shell('make tests_run')
                        shell('make clean')
                    }
                }
            """)
        }
    }
}
