# IAM

## IAM user

## IAM groups 

- by placing IAM users into groups, you can manage their permissions as a group, rathr than just individually
- users and groups can each have their own policies attached
- you could group users by role, team, security level, or whatever factors makes the most sense
- ifa user needs additional permission, you could either attach policy to user or move user to another group.

## IAM roles

roles can be assumed by STS.

if your provider is Security Assertion Markup Language (SAML), it's also supported.

if the user is not AWS users, they can be public users then using cognito with username/password.



