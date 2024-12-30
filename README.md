## Attention
![Static Badge](https://img.shields.io/badge/STATUS-not_ready!-%23FF0000)

> ⚠️ **Warning:** HEAVY DEVELOPMENT!
> 
> Not a single thing is stable or finished, everything is subject to change.
>
> Development ONLY, **NOT PRODUCTION READY**.

## Development mode
```
npm install
npx shadow-cljs watch app
```
start a ClojureScript REPL
```
npx shadow-cljs browser-repl
```
### Building for production

```
npx shadow-cljs release app
```

### Emacs

For Emacs simply run:
```emacs-lisp
(cider-jack-in-cljs)
;; M-x cider-jack-in-cljs too!
```
