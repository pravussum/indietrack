<template>
  <div id="mapContainer" class="flex-grow-1 h-100"></div>
</template>

<script>
import "leaflet/dist/leaflet.css";
import L from "leaflet";
import {ref} from "vue";
const API_URL = "http://localhost:8099";
// const API_URL = "";


export default {
  props:['trackId'],
  name: "TrackView",
  // data() {
  //   return {
  //     boundaries: [[51.0605034,13.6989567],[51.0759464,13.729364]],
  //     mapDiv: undefined,
  //     currentTrackPolyline: undefined,
  //   }
  // },
  async setup() {
    let boundaries = ref([[51.0605034,13.6989567],[51.0759464,13.729364]]);
    let mapDiv = ref(undefined);
    let currentTrackPolyline = ref(undefined);
    return {
      boundaries,
      mapDiv,
      currentTrackPolyline
    }
  },
  watch: {
    trackId(newTrackId) {
      this.updateTrackId(newTrackId);
    }
  },
  methods: {
    async updateTrackId(newTrackId) {
      console.log("changing trackId to " + newTrackId);
      if(newTrackId) {
        this.fetchTrackpoints(newTrackId)
        await this.fetchTrackInfo(newTrackId);
        await this.zoomMapToBoundaries();
      }
    },
    setupLeaflet: function() {
      this.mapDiv = L.map("mapContainer").setView([51.0605034,13.6989567], 13);
      L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
        maxZoom: 18
      }).addTo(this.mapDiv);
    },
    async fetchTrackInfo(trackId) {
      if(!trackId) {
        console.log("No trackId given.");
        return;
      }
      try  {
        console.log("Fetching track info for track " + trackId);
        let track = await (await fetch(`${API_URL}/track/${trackId}`)).json();
        console.log("Retrieved track info, updating boundaries.");
        this.boundaries = track.boundaries;
      } catch (e) {
        console.error(e);
      }
    },
    async fetchTrackpoints(trackId) {
      if(!trackId) {
        console.log("No trackId given.");
        return;
      }
      try {
        console.log("Fetching trackpoints for track "+ trackId);
        let trackpoints = await(await fetch(`${API_URL}/track/${trackId}/trackpoints`)).json();
        console.log("Fetched trackpoints, updating map.");
        if(this.currentTrackPolyline){
          this.currentTrackPolyline.removeFrom(this.mapDiv);
          this.currentTrackPolyline = undefined
        }
        const latLng = (trackpoints).map(this.mapToLatLng);
        this.currentTrackPolyline = L.polyline(latLng, {color: 'blue'}).addTo(this.mapDiv);
        this.currentTrackPolyline.bringToBack();

      } catch (e) {
        console.error(e)
      }
    },
    mapToLatLng(row) {
      return L.latLng(row.latitude, row.longitude)
    },
    async zoomMapToBoundaries() {
      this.mapDiv.fitBounds(this.boundaries);
    }
  },
  beforeRouteUpdate(newRoute){
    console.log("Setting boundaries")
    this.fetchTrackInfo(newRoute.params.id).then(() => this.zoomMapToBoundaries());
    this.fetchTrackpoints(newRoute.params.id)
  },
  mounted() {
    this.fetchTrackInfo(this.$route.params.id).then(this.setupLeaflet);
    this.fetchTrackpoints(this.$route.params.id)
  }
}
</script>

<style scoped>
  #mapContainer {
    width: 90vw;
    height: 50vh;
  }
</style>