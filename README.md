This jackson bug prevents a sub class' properties from being serialized when

1. some properties are in the base class,
2. the base class has an interface that directly or indirectly references
   the sub class, and
3. the base class's type is constructed first from the TypeFactory.

## Running

You can using the maven exec plugin:

Runs by passing the base class first into the TypeFactory (bug is triggered)
```
mvn compile exec:java -Dexec.mainClass="Bug"
```
Output:
```
baseJT = [simple type, class Bug$Base]
subJT  = [simple type, class Bug$Sub]
subJT.getSuperClass()  = [recursive type; Bug$Base
json(sub) = {"sub":2}      // missing "base":1 !!!
```

Runs by passing the sub class first inot the TypeFactory (bug is not triggered)
```
mvn compile exec:java -Dexec.mainClass="Bug" -Dexec.args="sub"
```
Output:
```
baseJT = [simple type, class Bug$Base]
subJT  = [simple type, class Bug$Sub]
subJT.getSuperClass()  = [simple type, class Bug$Base]
json(sub) = {"base":1,"sub":2}
```

