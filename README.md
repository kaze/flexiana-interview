# flexiana-interview

## tasks

- [x] Complete the function `(scramble str1 str2)` that returns `true` if a portion of `str1` characters can be rearranged to match `str2`, otherwise returns `false`.

#### Notes:

Only lower case letters will be used (a-z). No punctuation or digits will be included.
Performance needs to be considered.

#### Examples:

```clojure
(scramble? “rekqodlw” ”world”) ==> true
(scramble? “cedewaraaossoqqyt” ”codewars”) ==> true
(scramble? “katas”  “steak”) ==> false
```

- [ ] Create a web service that accepts two strings in a request and applies function `scramble?` from previous task to them.

- [ ] Create a UI in ClojureScript with two inputs for strings and a "scramble" button. When the button is fired it should call the API from previous task and display a result.

#### Notes

Please pay attention to tests, code readability and error cases.

---

Project is generated using Luminus version "4.14"

## prerequisites

You will need [Leiningen][1] 2.0 or above and [Node.js][2] installed.

[1]: https://github.com/technomancy/leiningen
[2]: https://nodejs.org/en/

## installation

Run in the project's root directory:

```bash
    lein deps
    npm install
```

## running

Start a web server for the application, run in the project root directory:

```bash
    lein run
```

Then visit the site at [http://localhost:3000](http://localhost:3000)

## testing

Run in the project's root:

```bash
    lein test
```

## development

Start a web server for the application, run in the project root directory:

```bash
    lein run
```

Start the frontend server in another terminal

```bash
    shadow-cljs watch app
```

In a third terminal you can run the test watcher as well:

```bash
    lein test-refresh
```

## license

Copyleft © 2021
