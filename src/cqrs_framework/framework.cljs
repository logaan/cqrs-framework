(ns cqrs-framework.framework
  (:require [cljsjs.virtual-dom]))

(defprotocol Render
  (render [app]))

(defrecord App [vdom state renderer]
  Render
  (render [app]
    (let [{:keys [tree root]} @vdom
          new-tree            (renderer app @state)
          patches             (js/virtualDom.diff tree new-tree)
          new-root            (js/virtualDom.patch root patches)]
      (swap! vdom assoc :tree new-tree :root new-root))))

(defn mount [mount-point state renderer]
  (let [vdom (atom {:tree nil :root nil})
        app  (App. vdom state renderer)
        tree (renderer app @state)
        root (js/virtualDom.create tree)]
    (swap! vdom assoc :tree tree :root root)
    (.appendChild mount-point root)
    (add-watch state ::re-render (fn [_ _ _ _] (render app)))
    app))
