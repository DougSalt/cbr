---

--- 

# Introduction


# NetLogo Extension

The philosophy is to provide a minimum framework in which to do case-base
reasoning. A case is made up of a decision, which must be a string; a state,
which may be any kind of NetLogo variable, and an outcome, which, again can be
any kind of NetLogo variable.

A typical case-base usage  implementation might have consist of creating a new
case-base using cbr:new, adding some cases using cbr:add and then matching an
outcome or outcomes using cbr:match or cbr:matches, respecitvely.

## Extension primitives

### cbr:new {#cbr:new}

#### Inputs

No inputs.

#### Outputs 

A casebase variable.

#### Purpose

This creates an empty case-base. Cases may be added by using [cbr:add](#cbr:add)

#### Example

```
  let some-case-base cbr:new 
```

This creates a new, empty case base. The scope is the case base is the same as
the scope of the variable to which the case base is assigned.

## cbr:add {#cbr:add}

#### Inputs

#### Outputs 

#### Purpose

#### Example

### cbr:combine

#### Inputs

#### Outputs 

#### Purpose

#### Example

### cbr:all

#### Inputs

#### Outputs 

#### Purpose

#### Example

### cbr:remove

#### Inputs

#### Outputs 

#### Purpose

#### Example

### cbr:state

#### Inputs

#### Outputs 

#### Purpose

#### Example

### cbr:decision

#### Inputs

#### Outputs 

#### Purpose

#### Example

### cbr:equal

#### Inputs

#### Outputs 

#### Purpose

#### Example

### cbr:outcome

#### Inputs

#### Outputs 

#### Purpose

#### Example

### cbr:forget

#### Inputs

#### Outputs 

#### Purpose

#### Example

### cbr:get-earliest

#### Inputs

#### Outputs 

#### Purpose

#### Example

### cbr:set-earliest

#### Inputs

#### Outputs 

#### Purpose

#### Example

### cbr:get-max-size

#### Inputs

#### Outputs 

#### Purpose

#### Example

### cbr:set-max-size

#### Inputs

#### Outputs 

#### Purpose

#### Example

### cbr:get-rank {#cbr:get-rank}

#### Inputs

#### Outputs 

This reports an integer as a NetLogo number indicating the rank of the case
within the case-base. This is by default zero unless otherwise set by
[cbr:set-rank](#cbr:set-rank). This is used to detemine case precedence if
there is a draw when trying to match a case-base.

#### Purpose

#### Example

### cbr:set-rank {#cbr:set-rank}

#### Inputs

+ A case base of the type [cbr:new](#cbr:new)

+ An integer rank of 0 or above.

#### Purpose

This is a command and has no return. It sets the rank of a particular case in a
case-base. If, when matching cases, two cases are indentical, then it is the
case with the highest rank that will be selected. If two or more cases are
matched and have the same rank, then if the match is made using
[cbr:match](#cbr:match), then a random selection from the matching cases is
returned. If [cbr:matches](#cbr:matches) is used, then all the matches with the
same rank are returned.

The default rank is always 0, and the value may be interrogated using
[cbr:get-rank](#cbr:get-rank).

#### Example

### cbr:get-time

#### Inputs

#### Outputs 

#### Purpose

#### Example

### cbr:set-time

#### Inputs

#### Outputs 

#### Purpose

#### Example

### cbr:resize

#### Inputs

#### Outputs 

#### Purpose

#### Example

### cbr:lambda

#### Inputs

#### Outputs 

#### Purpose

#### Example

### cbr:match {#cbr:match}

#### Inputs

#### Outputs 

#### Purpose

#### Example

### cbr:matches {#cbr:matches}

#### Inputs

#### Outputs 

#### Purpose

#### Example

### cbr:yes

#### Inputs

#### Outputs 

#### Purpose

#### Example

### cbr:no

#### Inputs

#### Outputs 

#### Purpose

#### Example

### cbr:invalid

#### Inputs

#### Outputs 

#### Purpose

#### Example

# Illustrations

# Discussion and Conclusions
