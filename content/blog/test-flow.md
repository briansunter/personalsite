+++
title = "Testing"
description = """
What are transducers and why would I want to use them?
  """
uuid = "test"
tags = ["blog"]
+++
# Testing Flow

# Why Test?
Testing is one of the most powerful techniques I know of in software development and I don't think it gets enough attention. In long running projects a specification of the system is as important as the system itself. The first time I tried TDD was very difficult, but over time I noticed a number of big benefits. I consider it one of the main antidotes to the tar pit.

## The Tar Pit
The Tar Pit is a term from a software engineering book "The Mythical Man Month". It describes the tendency for feature delivery to slow over time due to increasing complexity and communication overhead. Testing helps address some of these problems.

## Faster Feedback Loop
When I wrote tests during development, I spent a lot less time convincing myself the code worked. I had an auto test runner, refactored until the tests passed, and then manually tested. Opening postman and clicking through UIs is slow. The critical path of writing the code should be as optimized as possible.

## Safely Make Changes
Software is continually evolving and never done. Requirements will change, new features will be required, and we will discover better ways of doing things during the life of a project. Testing gives us some assurance that we can add features and refactor without breaking existing features.

## Correctness
Testing validates that the code behaves as you expect and helps you double check your work. In dynamic languages it protects you against many syntax errors. Testing can only go so far in proving correctness but it can signal when an unexpected change in behavior occurs.

# Types of Testing
There are different types of testing depending on what you're trying to validate.

## Unit Testing
Unit tests run quickly and test individual components of your program, usually functions. These should ideally be run and written during development. They should comprise the majority of testing and be your first line of defense.

<script src="https://unpkg.com/chai@4.0.2/chai.js"> </script>
<script>
var expect = chai.expect;
function it(msg, fn) {
  return fn().__flags.message || true;
}
</script>

``` js
it("Addition should work", () =>
 expect(3 + 1).to.equal(4)
)
```

## Integration Testing
Integration testing verifies the behavior of "external" components. It's not always possible to simulate all external components at once, so this is usually a subset. An example of this would be hitting a rest api with an in memory database with an http client.

## Functional Testing
Testing verifies the requirements of the system as a whole.

## Performance Testing
Protects against introducing performance degradation.

# How to Write Tests

## Specify Tests in the Acceptance Criteria
Make sure to budget time for testing in the development process

## Write Testable Code

### Clean Architecture

### Functional Programming

# When to Run Tests

## Local Auto Test Runner

## Test on Pull Requests

## Test on Merge
