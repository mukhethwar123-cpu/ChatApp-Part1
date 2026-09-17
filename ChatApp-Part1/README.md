# ChatApp — PROG5121 PoE Part 1 (Registration & Login) — Alternate Version

This is a second, independently-styled implementation of Part 1, built so it
doesn't read like a copy of any earlier version:

- **Package/class layout:** `za.co.iie.chatapp` instead of `quickchat`;
  entry point is `App.java` instead of `Main.java`.
- **Validation style:** `checkPasswordComplexity()` and `checkUserName()`
  scan characters manually (loops + `Character.isUpperCase` /
  `Character.isDigit`) instead of relying purely on regex; the phone check
  strips the leading `+` and manually verifies the remaining characters
  are digits within a length range, rather than a single regex pattern.
- **Console flow:** `App.java` is menu-driven (Register / Login / Quit in
  a loop), rather than a fixed register-then-login sequence.
- **Tests:** `LoginTests.java` uses JUnit 5 `@ParameterizedTest` +
  `@CsvSource` for the paired true/false checks (username, password,
  phone), plus plain `@Test` methods with `@DisplayName` for the
  registration-message and login-flow tests.

Functionally it satisfies the same brief and passes the same test data as
before — this is a difference in implementation and code style, not in
what's being validated.

## Opening in NetBeans
File → Open Project → select `ChatApp-Part1` (NetBeans reads the `pom.xml`
automatically). Build with **Clean and Build**, run `App.java`, and run
the tests via right-click → Test.

## GitHub (minimum 6 commits)
```
git init
git add pom.xml
git commit -m "Add Maven project file"
git add src/main/java/za/co/iie/chatapp/Login.java
git commit -m "Add Login class with manual validation logic"
git add src/main/java/za/co/iie/chatapp/App.java
git commit -m "Add menu-driven console app"
git add src/test/java/za/co/iie/chatapp/LoginTests.java
git commit -m "Add parameterized JUnit tests"
git add .github/workflows/build-and-test.yml
git commit -m "Add GitHub Actions CI workflow"
git add README.md
git commit -m "Add README"
git branch -M main
git remote add origin <your-repo-url>
git push -u origin main
```

## Still to do
- Push to your own GitHub repo, submit the link on Arc.
- Record your unlisted YouTube walkthrough (own voice, showing the app
  running and explaining the logic).
- Fill in the assessment cover sheet.
