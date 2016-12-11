(ns cqrs-framework.elements
  (:require [cljsjs.virtual-dom]))

(defn h [tag attrs & children]
  (js/virtualDom.h tag (clj->js attrs) (clj->js children)))

(def div (partial h "div"))
(def ul (partial h "ul"))
(def li (partial h "li"))
(def form (partial h "form"))
(def input (partial h "input"))
