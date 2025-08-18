import com.google.protobuf.gradle.id

plugins {
	`java-library`
	id("com.google.protobuf") version "0.9.4"
    id("maven-publish")
}

group = "com.voyachek.pharmacy"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
    withSourcesJar()
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

extra["springGrpcVersion"] = "0.10.0"
extra["ioGrpcServicesVersion"] = "1.72.0"

dependencies {
	implementation("io.grpc:grpc-services:${property("ioGrpcServicesVersion")}")
	implementation("org.springframework.grpc:spring-grpc-spring-boot-starter:${property("springGrpcVersion")}")
}

protobuf {
	protoc {
		artifact = "com.google.protobuf:protoc:4.30.2"
	}
	plugins {
		id("grpc") {
			artifact = "io.grpc:protoc-gen-grpc-java:1.72.0"
		}
	}
	generateProtoTasks {
		all().forEach {
			it.plugins {
				id("grpc") {
					option("@generated=omit")
				}
			}
		}
	}
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}

sourceSets {
    main {
        java {
            srcDirs(
                "src/main/java",
                "build/generated/source/proto/main/java",
                "build/generated/source/proto/main/grpc"
            )
        }
    }
}

tasks.named<Jar>("sourcesJar") {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}