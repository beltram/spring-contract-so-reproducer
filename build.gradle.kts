import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES
import org.springframework.cloud.contract.verifier.config.TestFramework
import org.springframework.cloud.contract.verifier.config.TestMode
import org.springframework.cloud.contract.verifier.plugin.ContractVerifierExtension

buildscript {
	repositories {
		mavenCentral()
		maven("https://repo.spring.io/milestone")
	}
	dependencies {
		val springCloudContractVersion = "2.1.0.RC3"
		classpath("org.springframework.cloud:spring-cloud-contract-gradle-plugin:$springCloudContractVersion")
	}
}
plugins {
	val kotlinVersion = "1.3.20"
	kotlin("jvm") version kotlinVersion
	kotlin("plugin.spring") version kotlinVersion
	id("org.springframework.boot") version "2.1.2.RELEASE"
}

val springCloudVersion by extra { "Greenwich.RELEASE" }
val kotlinVersion by extra { "1.3.20" }
val springCloudContractVersion by extra { "2.1.0.RC3" }

apply {
	plugin("kotlin")
	plugin("kotlin-spring")
	plugin("org.springframework.boot")
	plugin("io.spring.dependency-management")
	plugin("spring-cloud-contract")
}

group = "com.beltram"
version = "0.0.1-SNAPSHOT"

repositories {
	mavenCentral()
	maven("https://repo.spring.io/milestone")
}

the<DependencyManagementExtension>().apply {
	imports {
		mavenBom(BOM_COORDINATES) { bomProperty("kotlin.version", kotlinVersion) }
		mavenBom("org.springframework.cloud:spring-cloud-contract-dependencies:$springCloudContractVersion")
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation(kotlin("stdlib-jdk8"))
	implementation(kotlin("reflect"))
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.cloud:spring-cloud-starter-contract-verifier")
}

tasks.withType<KotlinCompile> {
	kotlinOptions { jvmTarget = "1.8" }
}

configure<ContractVerifierExtension> {
	testMode = TestMode.WEBTESTCLIENT
	testFramework = TestFramework.JUNIT5
}

tasks.withType<Test>().configureEach {
	useJUnitPlatform()
}