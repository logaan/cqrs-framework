(ns cqrs-framework.framework)

(defprotocol Render
  (render [app]))

(defrecord App [state renderer]
  Render
  (render [app]
    (renderer app @state)))

(defn app [state renderer]
  (let [a  (App. state renderer)]
    (add-watch (:state a) ::re-render (fn [_ _ _ _] (render a)))
    a))


