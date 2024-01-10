# Y4-JDK-8

y4-jdk is an [openjdk8u](https://github.com/openjdk/jdk8u) branch that is designed with a great emphasis on security features (fix potential CVE vulnerabilities, deserialization vulnerabilities, remote code/command execution vulnerabilities, etc., without affecting the normal use of functionalities)

all [downloadable release]() versions are built using [github actions](https://github.com/4ra1n/jdk-8/actions) and have passed [jtreg](https://openjdk.org/jtreg/) regression tests

Supported versions:
- Windows x64 (JDK/JRE)
- Linux x64 (JDK/JRE)
- MacOS x64 (JDK/JRE)

## CHANGE LOG

| issue id | jtreg windows | jtreg linux | jtreg macos | actions | release |
|:---------|:--------------|:------------|:------------|:---------------|:--------|
|[Y4-00001](https://github.com/4ra1n/jdk-8/issues/1)|✔️|✔️|✔️|[workflow](https://github.com/4ra1n/jdk-8/actions/runs/7475817976)|❌️|
|[Y4-00002](https://github.com/4ra1n/jdk-8/issues/2)|❌️|❌️|❌️|❌️|❌️|
