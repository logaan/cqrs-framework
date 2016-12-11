(ns cqrs-framework.core
  (:require [datascript.core :as d]
            [cljsjs.virtual-dom]))

(enable-console-print!)

(defrecord App [state])

(def app
  (map->App {:state (atom {:counter 0})}))

(defn render [state]
  (aset js/document.body.children.app "innerText" (:counter state)))

(add-watch (:state app) :key (fn [k r os ns] (render ns)))

(defn on-js-reload []
  (render @(:state app)))

;; controllers/counter.cljs

(defprotocol Counter
  (increment [app])
  (decrement [app])
  (reset [app]))

(extend-type App
  Counter
  (increment [{:keys [state] :as app}]
    (swap! state update-in [:counter] inc))
  (decrement [{:keys [state]}]
    (swap! state update-in [:counter] dec))
  (reset [{:keys [state]}]
    (swap! state assoc :counter 0)))
