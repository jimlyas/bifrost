## Motivation

`bifrost` is a Gradle plugin to help manage multi-project switching between pre-built artifacts and local module
dependencies.

Multi-project enables developers by separating the code into modules or projects to improve code ownership, scalability,
and so on.
But the more modules you have, the fewer modules you'll use for developing.

For example, developing a new feature or bug fixing usually needs no more than 4 modules.
If your project has more than 100 modules, that's like less than 5% of your whole codebase.

But the thing is, when your project depends on a local project, even though you don't really make changes there, Gradle
will still use that module during builds.

This plugin will help you switch between prebuilt artifacts _(that you published)_ and local module dependency
seamlessly.

So you can only load the modules that you'll use, be it for developing, bug fixing, or debugging,
and tell Gradle to include the other modules you don't use in their prebuilt artifact.

This also improve build times because Gradle can skip any tasks for unused modules because they're just prebuilt
artifact now and will be treated just like any dependency.

## Prerequisites

Before using any `bifrost` plugin, the project need to implement publishing mechanism.
Either to an instance of remove maven repository or maven local.

You can follow this documentation for setting up a publishing:

- [Android Library](https://developer.android.com/build/publish-library)
- [Java or Kotlin Library](https://docs.gradle.org/current/userguide/publishing_maven.html)

`bifrost` will use artifact from those maven repository and substitute it with local project when needed.

> Make sure to name your artifact the same as your project name, `bifrost` relies on those

## Quick Start

### Gradle Properties

Add **open.bifrost.gate** to your `gradle.properties` file:

```properties
# ...
# Other properties
open.bifrost.gate=false
```

It is recommended to set the default value to `false` so the Plugin doesn't do anything initially.

### Creating Version Catalog

`bifrost` is a bridge between realms. These realms need to be registered to a version catalog.
Create a new TOML file in `gradle/realm.versions.toml` to look like this:

```toml
[versions]
module = "0.1.0-SNAPSHOT" # You can customize version based on your prebuilt artifact

# List all your projects here
[libraries]
module-a = { group = "your.group.name", name = "module-a", version.ref = "module" }
module-b = { group = "your.group.name", name = "module-b", version.ref = "module" }
module-c = { group = "your.group.name", name = "module-c", version.ref = "module" }

[bundles]
active = [] # Leave it empty for now
```

Make sure the entry name inside `libraries` have the same name as the library name.

### Setting up `settings.gradle.kts`

Add the `io.github.jimlyas.bifrost.settings` plugin to the setting script

```kotlin
pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

plugin {
    id("io.github.jimlyas.bifrost.settings") // Add this 
}

include(":always-on-module") // Module that you will always open

// Add this too
configure<BifrostSettingExtension> {
    // Add module that you'll change as prebuilt artifact later
    includeModule(":module-a", ":module-b", ":module-c")
}
```

### Setting up `build.gradle.kts`

Add the `io.github.jimlyas.bifrost.project` plugin to all the projects and replace all the module dependencies with
dependency declared from realm version catalog

```kotlin
plugin {
    // ...
    // Other plugins
    id("io.github.jimlyas.bifrost.project") // Add this
}

dependencies {
    // Before
    // implementation(project(":module-b"))
    // implementation(project(":module-c"))

    // After
    implementation(realm.module.b)
    implementation(realm.module.c)
}
```

## Usage

To enable `bifrost`, change the **open.bifrost.gate** properties to `true`:

```properties
# ...
# Other properties
open.bifrost.gate=true
```

Then you can update the `active` entries inside `realm.versions.toml` to include module you want to load locally:

```toml

# Update this
[bundles]
active = ["module-a", "module-b"]
```

For example, configuration above will make sure Gradle will load project `module-a` and `module-b` but will load
`module-c` as dependency.

> Do not commit any changes to `gradle.properties` and `realm.versions.toml` file,
> move them to different change list so you don't accidentally commit them.

## Disclaimer

Bifrost currently does not support nested Gradle project.

## License

```
MIT License

Copyright © 2025 Jimly Asshiddiqy

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute,
sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
IN THE SOFTWARE.
```