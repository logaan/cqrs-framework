(ns cqrs-framework.core
  (:require [datascript.core :as d]
            [cqrs-framework.controller]
            [cqrs-framework.framework :as f]
            [cljsjs.virtual-dom]))

(def h js/virtualDom.h)

(enable-console-print!)

(defn render [app {:keys [counter]}]
  (h "div" #js {} (str counter)))

(defonce app
  (f/mount (js/document.getElementById "app")
           (atom {:counter 0})
           render))

(defn on-js-reload []
  (f/render app))
