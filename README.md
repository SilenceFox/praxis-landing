
### Development mode
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
