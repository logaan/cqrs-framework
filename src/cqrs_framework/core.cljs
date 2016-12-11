(ns cqrs-framework.core
  (:require [datascript.core :as d]
            [cqrs-framework.controller]
            [cqrs-framework.framework :as f]
            [cljsjs.virtual-dom]))

(enable-console-print!)

(defn render [app state]
  (aset js/document.body.children.app "innerText" (:counter state)))

(def app
  (f/app (atom {:counter 0})
          render))

(defn on-js-reload []
  (f/render app))

(defonce rendered
  (f/render app))
