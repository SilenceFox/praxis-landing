(ns praxis.core
  (:require
   [clojure.string :as str]
   [reagent.core :as r]
   [reagent.dom :as d]))

;; ----------------------
;; Helper Functions

(defn with-props
  "Transforms a map of CSS properties into a :style map, with special handling for CSS custom properties (variables).

   This function is designed to work seamlessly with Open Props or similar systems where CSS custom properties
   (variables) are used. If a value starts with '--' (indicating a CSS custom property), it is automatically wrapped
   in the 'var()' function to make it a valid CSS variable. This ensures that the CSS variables are correctly interpreted
   and applied in the styles.

   Arguments:
   - props: A map of CSS property-value pairs. For example:
     {:background '--primary', :color 'red', :margin '10px'}

   Returns:
   - A map containing a single key, :style, where the value is another map of CSS properties. If any property value
     starts with '--' (denoting a CSS variable), it is wrapped with 'var()' to be correctly applied as a CSS custom
     property. Regular values are passed through without modification.

   Example:
   (with-props {:background '--primary' :color 'red' :margin '10px'})
   => {:style {:background 'var(--primary)', :color 'red', :margin '10px'}}

   This function simplifies the integration of CSS custom properties within dynamic Reagent components, ensuring that
   variables are handled consistently, while allowing other styles to be passed through as usual. It's particularly useful
   for working with systems like Open Props, which rely heavily on CSS variables for design tokens and responsive styles."
  [props]
  (let [wrap-var (fn [v]
                   (if (and (string? v) (str/starts-with? v "--"))
                     (str "var(" v ")") v))]
    {:style (into {} (map (fn [[k v]] [k (wrap-var v)]) props))}))

(defn s!
  "A shorthand for `with-props` that allows optional CSS overrides. If no overrides are provided, it defaults to an empty map.

   Arguments:
   - css-overrides (optional): A map of CSS properties to override. Defaults to `{}` if not provided.

   Returns:
   - A map containing a `:style` key with the processed CSS properties, with CSS variables handled correctly.

   Example:
   (s! {:background '--primary' :color 'blue'})
   => {:style {:background 'var(--primary)', :color 'blue'}}

   (s!)
   => {:style {}}

   Useful for simplifying component styling when overrides are optional."
  [& css-overrides]
  (with-props (-> css-overrides
                  first
                  (or {}))))

;; ------------------------
;; Components

(defn Spacer [css]
  [:hr (s! css)])

(defn Nav []
  [:nav (s! {:background "--gray-5"
             :padding "--size-3"
             :box-shadow "--shadow-1"
             :position "sticky"
             :top "0"
             :z-index "1000"}) "Nav"])

(defn Footer []
  [:footer (s! {:background "--gray-9"
                :color "--gray-1"
                :padding "--size-3"
                :text-align "center"})])

(defn Button [inner]
  [:button (s! {:background "--primary"
                :color "--gray-1"
                :padding "--size-3"
                :border-radius "--radius-2"
                :cursor "pointer"
                :transition "background 0.3s ease"
                :hover {:background "--primary-dark"}}) inner])

(defn Main [& content]
  [:main (s! {:max-width "800px"
              :margin "0 auto"
              :padding "--size-3"})

   (map-indexed (fn [idx child]
                  (with-meta child {:key idx}))
                content)])

(defn About
  "About the project but hardcoded."
  [css]
  [:section#about (s! css)
   [:h1 "About Praxis"]
   [Spacer]
   [:del "Honestly... I dont really know myself, but:"]
   [:p "It's a prayer app for" [:b " Orthodox Christians"]
    "! Helps you with fasting days and keeps you updated "
    "on the Church's calendar."]])

;; -------------------------
;; Views

(defn home-page []
  [:div.main-container
   [Nav]
   [Main
    [About {:color "--red-2"} ]
    ;; [Downloads]
    ;; [Links]
    ;; []
    [:h1 "Funny"]
    [:p "Content"] ]
   [Footer]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (d/render [home-page
             {:gibberish (js/Date.now)}]
            (.getElementById js/document "app")))

(defn ^:export init! []
(mount-root))
