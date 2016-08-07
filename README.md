[tick]: http://i.imgur.com/DYNLLgl.png "Yes"
[cross]: http://i.imgur.com/Fi79hmI.png "No"

# Git Merge Tool

Tool for fixing git merge conflicts in files and directories.<br>
Created purely for learning puroposes.

* * *

## Installation

For now installation works only on Ubuntu. Though you can run the jar file
itself on windows / other platforms.

* * *

## Supported languages:

| Languages  | Syntax Highlighting | Autocomplete  |
| ---------- |:-------------------:| :------------:|
| CSS        | ![Yes][tick]        | ![Yes][tick]  |
| htaccess   | ![Yes][tick]        | ![No][cross]  |
| HTML       | ![Yes][tick]        | ![Yes][tick]  |
| Javascript | ![Yes][tick]        | ![Yes][tick]  |
| JSON       | ![Yes][tick]        | ![No][cross]  |
| Perl       | ![Yes][tick]        | ![Yes][tick]  |
| PHP        | ![Yes][tick]        | ![Yes][tick]  |
| Ruby       | ![Yes][tick]        | ![No][cross]  |
| Scala      | ![Yes][tick]        | ![No][cross]  |
| Shell      | ![Yes][tick]        | ![Yes][tick]  |
| SQL        | ![Yes][tick]        | ![No][cross]  |
| XML        | ![Yes][tick]        | ![Yes][tick]  |

* * *

## Adding new languages to the editor:

  1. Create compatible class according to [this](https://github.com/bobbylight/RSTALanguageSupport).<br><br>
  - Add your newly created language support class to:<br><br>
  ```
  com.wpruszak.blitzch.lib.factories.impl.CompleteLanguageSupportFactory
  ```
  <br><br>
   - Add string constant which will follow below naming convention:
   <br><br>
   ```java
   private static final String PHP_LANGUAGE_SUPPORT = "php";
   ```
   Where constant name should be:  ${LANGUAGE_NAME}_LANGUAGE_SUPPORT and
   value should be the most common extension of the file used by that language.
   In case of multiple file formats feel free to add multiple constants with multiple extensions or just create array / set of those extensions under one constant.<br><br>
  - Add another else clause to the factory, so that it will catch our new language support class. For example when we are trying to add C++ language support the file should look like this:   
  constant:
  ```java
  private static final String CPP_LANGUAGE_SUPPORT = "cpp";
  ```
  and below code:
  ```java
  @Override
  	public CompleteLanguageSupportBean createCompleteLanguageSupport(String type) {

        if(type.equals(CompleteLanguageSupportFactoryImpl.PHP_LANGUAGE_SUPPORT)) {
            return new CompleteLanguageSupportBean(new PhpLanguageSupport(), SyntaxConstants.SYNTAX_STYLE_PHP);    
        } ... {
          ...
        } else if (type.equals(CompleteLanguageSupportFactoryImpl.CPP_LANGUAGE_SUPPORT)) {
            return new CompleteLanguageSupportBean(new CppLanguageSupport(), SyntaxConstants.SYNTAX_STYLE_CPP);
        }
  ```
  If you don't have autocomplete class, just use this instead:
  ```java
  @Override
  	public CompleteLanguageSupportBean createCompleteLanguageSupport(String type) {

        if(type.equals(CompleteLanguageSupportFactoryImpl.PHP_LANGUAGE_SUPPORT)) {
            return new CompleteLanguageSupportBean(new PhpLanguageSupport(), SyntaxConstants.SYNTAX_STYLE_PHP);    
        } ... {
          ...
        } else if (type.equals(CompleteLanguageSupportFactoryImpl.CPP_LANGUAGE_SUPPORT)) {
            return new CompleteLanguageSupportBean(null, SyntaxConstants.SYNTAX_STYLE_CPP);
        }
  ```
  - It's done.

* * *

## Resources:

[Javadoc](https://rawgit.com/Setzo/Git-Merge-Tool/master/BlitzCompanyHelper/doc/index.html)<br>
[UML](https://raw.githubusercontent.com/Setzo/Git-Merge-Tool/master/BlitzCompanyHelper/src/com/wpruszak/blitzch/structure.png)

* * *

## Contact

As a side note, feel free to contact me if something is unclear to you, or if you just need more complete API.<br>
setzo94@gmail.com
