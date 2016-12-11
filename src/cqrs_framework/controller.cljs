(ns cqrs-framework.controller
  (:require [cqrs-framework.framework :as f]))

(defprotocol Counter
  (increment [app])
  (decrement [app])
  (reset [app]))

(extend-type f/App
  Counter
  (increment [{:keys [state] :as app}]
    (swap! state update-in [:counter] inc))
  (decrement [{:keys [state]}]
    (swap! state update-in [:counter] dec))
  (reset [{:keys [state]}]
    (swap! state assoc :counter 0)))
