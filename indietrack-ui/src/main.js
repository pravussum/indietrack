import { createApp } from 'vue'
import App from './App.vue'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap/dist/js/bootstrap'
import TrackView from "@/components/TrackView";
import TrackList from "@/components/TrackList";
import {createRouter, createWebHashHistory} from "vue-router";

const routes = [
    { path: '/trackview/:id', component: TrackView},
    { path: '/tracklist', component: TrackList}
];
const router = createRouter({
        history: createWebHashHistory(),
        routes
    }
)

const app = createApp(App);
app.use(router);
// app.config.compilerOptions.isCustomElement = (tag) => {
//     return tag.startsWith('ion-');
// };
app.directive('bsevent', {
    bind: function bsEventCreate(/*el, binding*/) {
        // let method = binding.value || (() => { });
        console.log("bseventCreate")
        // $(el).on(binding.arg.replaceAll(/_/g, "."), (event) => { method(event); });
    },

    // unbind(el, binding) {
    //     // $(el).off(binding.arg.replace(/_/, "."));
    //  },
})
app.mount('#app');


