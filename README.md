# intellij-serverless
A Plugin for the Intellij platform (IDEA, GoLand, PhpStorm, ...) to help with Serverless applications

## Current state
It has auto-completion for the first two "layers" of the serverless.yaml file. It's by no means something you'd use in production, although it wouldn't really have any nasty side-effects. 

## TODOs
- [ ] Limit it to `Serverless.yaml` files, and not all `yaml` files
- [ ] Add all fields
- [ ] Automatically add the colon `:` and newline upon selection (if needed)
- [ ] Distinguish between different providers (AWS, Azure, ...)
- [ ] Detect support for the 'serverless' binary, and opt to install it via `npm install -g serverless`

## PRs
All PRs should be reviewed within the day. 
