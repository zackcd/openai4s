# openai4s - OpenAI API Scala Client Library

This is an open source client library for the OpenAI API, which allows developers to easily interact with the OpenAI GPT
language model and other AI-powered tools using Scala.

## Installation

To use this library, add the following dependency to your SBT build file:

```scala
libraryDependencies += "" %% "openai4s" % "0.1.0"
```

## Usage

To use the library, you will need to have an OpenAI API key. If you do not have a key, you can sign up for one on the
OpenAI website.

Once you have a key, you can authenticate with your key:

```scala
import com.openai.OpenAi
import com.openai.models._

// Either set the config explicitly
val config = new OpenAiConfig(...)
val openai = new OpenAi(config)

// Or set your credentials via environment variable and create the client instance without any parameters.
val openai = new OpenAi()
```

You can then use the library to interact with the OpenAI API. For example, to generate text using the GPT-3 language
model, you can use the following code:

```scala
val prompt = Prompt(
  engine = "davinci",
  prompt = "Once upon a time",
  maxTokens = 5
)

val response = openai.completions.create(prompt)

println(response.choices(0).text)
```

This will generate a short piece of text based on the given prompt.

## Contributing

Contributions to this library are welcome! If you find a bug or have a feature request, please open an issue on the
GitHub repository.

If you would like to contribute code, please fork the repository and submit a pull request with your changes. Before
submitting a pull request, please make sure that your code passes the existing tests and includes new tests for any new
functionality.

## License

This library is released under the MIT License. See the LICENSE file for more information.
