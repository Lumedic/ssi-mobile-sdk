Pod::Spec.new do |spec|
    spec.name                     = 'kotlin_multiplatform_agent'
    spec.version                  = '1.0-SNAPSHOT'
    spec.homepage                 = 'https://github.com/Lumedic/ssi-mobile-sdk.git'
    spec.source                   = {:git => 'https://github.com/Lumedic/ssi-mobile-sdk.git', :branch => 'develop'}
    spec.authors                  = 'Luxoft, a DXC Technology Company'
    spec.license                  = 'Apache 2.0'
    spec.summary                  = 'Kotlin multiplatform project'

    spec.static_framework         = true
    #spec.vendored_frameworks      = "build/cocoapods/framework/ssi_agent.framework"
    spec.libraries                = "c++"
    spec.module_name              = "#{spec.name}_umbrella"

    spec.ios.deployment_target = '8.0'

    #spec.dependency	'libsodium', '~> 1.0.12'
    #spec.dependency	'libzmq-pw', "4.2.2"
    #spec.dependency	'OpenSSL-XM'
    #spec.dependency	'libindy', '1.15.0'
    spec.dependency 'kotlin_multiplatform_agent', :path => '../../..'

    spec.pod_target_xcconfig = {
        'KOTLIN_TARGET[sdk=iphonesimulator*]' => 'ios_x64',
        'KOTLIN_TARGET[sdk=iphoneos*]' => 'ios_arm',
        'KOTLIN_TARGET[sdk=watchsimulator*]' => 'watchos_x64',
        'KOTLIN_TARGET[sdk=watchos*]' => 'watchos_arm',
        'KOTLIN_TARGET[sdk=appletvsimulator*]' => 'tvos_x64',
        'KOTLIN_TARGET[sdk=appletvos*]' => 'tvos_arm64',
        'KOTLIN_TARGET[sdk=macosx*]' => 'macos_x64'
    }

    spec.script_phases = [
        {
            :name => 'Build kotlin_multiplatform_agent',
            :execution_position => :before_compile,
            :shell_path => '/bin/sh',
            :script => <<-SCRIPT
                set -ev
                REPO_ROOT="$PODS_TARGET_SRCROOT"
                "$REPO_ROOT/gradlew" -p "$REPO_ROOT" ::syncFramework \
                    -Pkotlin.native.cocoapods.target=$KOTLIN_TARGET \
                    -Pkotlin.native.cocoapods.configuration=$CONFIGURATION \
                    -Pkotlin.native.cocoapods.cflags="$OTHER_CFLAGS" \
                    -Pkotlin.native.cocoapods.paths.headers="$HEADER_SEARCH_PATHS" \
                    -Pkotlin.native.cocoapods.paths.frameworks="$FRAMEWORK_SEARCH_PATHS"
            SCRIPT
        }
    ]
end