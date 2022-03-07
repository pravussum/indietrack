<!--<template >-->
<template >
<!--  <p>Tracklist</p>-->
<!--  <li v-for="item in items" :key="item.id">-->
<!--    {{ item.name }}-->
<!--  </li>-->
  <Suspense>
  <div class="d-flex h-100" >
    <div class="tracklist-container flex-column">
      <div class="accordion accordion-flush" id="tracklistAccordion">
        <div class="accordion-item" v-for="track of tracks" :key="track.id">
          <div class="accordion-header" id="heading-{{track.id}}" data-bs-toggle="collapse" v-bind:data-bs-target="'#collapse-' + track.id">
            <h2 class="mb-0 accordion-button"><ion-icon
                v-bind:src="getActivityType(track) == 'run'? 'http://localhost:8080/running.svg' : null"
                v-bind:name="getActivityIcon(getActivityType(track))" class="icon-large icon-rpad"></ion-icon><h4>{{track.name}}</h4></h2>
          </div>

          <track-list-item :track=track  :id="'collapse-' + track.id" class="accordion-collapse collapse" data-bs-parent="#tracklistAccordion" :ref="componentUpdate" @shown="onShown(track)"></track-list-item>

<!--          <div >-->
<!--            <div>-->
<!--              <div><ion-icon name="analytics-outline"  class="icon-medium icon-rpad"></ion-icon>{{fmt2Digits(track.distance / 1000)}} km</div>-->
<!--              <div><ion-icon name="stopwatch-outline"  class="icon-medium icon-rpad"></ion-icon>{{fmtMinutes(getDurationInMin(track))}}</div>-->
<!--              <div><ion-icon name="speedometer-outline"  class="icon-medium icon-rpad"></ion-icon>{{fmtKilometers(getAvgInKmh(track))}}</div>-->


<!--            </div>-->
<!--          </div>-->
        </div>
      </div>
    </div>
    <div class="d-flex flex-column flex-grow-1">
      <!--      <app-trackview [track]="selectedTrack" [marker]="trackPosToMark" [selectionMode]="segCreationMode" class="flex-grow-1 h-100"></app-trackview>-->
      <track-view class="flex-grow-1 h-100" :track-id="selectedTrack ? selectedTrack.id : undefined"></track-view>
    </div>
  </div>
  </Suspense>
</template>


<script>

// const API_URL = "http://localhost:8099";
import TrackView from "@/components/TrackView";
import TrackListItem from "@/components/TrackListItem";
// import {ref} from "vue";
const API_URL = "http://localhost:8099";
// const API_URL = "";

export default {
  components: {TrackListItem, TrackView},
  data() {
    return {
      tracks: undefined,
      selectedTrack: undefined,
    }
  },
  methods: {
    componentUpdate(el) {
      el.domElementUpdate(el.$el);
    },
    onShown(track){
      this.onSelect(track)
    },
    async fetchTrackList() {
      try  {
        this.tracks = await (await fetch(`${API_URL}/track`)).json();
      } catch (e) {
        console.error(e);
      }
    },
    async onSelect(track) {
      console.log("Selected track" + track.id);
      this.selectedTrack = track;
    },
    getActivityType(track) {
      let avgSpeed = this.getAvgInKmh(track);
      if(avgSpeed < 7) {
        return "walk"
      } else if(avgSpeed < 17) {
        return "run"
      } else if(avgSpeed < 45) {
        return "bike"
      } else return "moto";
    },
    getActivityIcon(activityType) {
      switch (activityType) {
        case "walk": return "walk";
        case "run": return "";
        case "bike": return "bicycle";
        case "moto": return "car"
      }
    },
    getAvgInKmh(track) {
      if(track) {
        return (track.distance / 1000) / (this.getDurationInMin(track) / 60);
      } else return undefined;
    },
    getDurationInMin(track) {
      if(track) {
        return (new Date(track.endTime).getTime() - new Date(track.startTime).getTime()) / 60000;
      } else return undefined;
    },
  },
  mounted() {
    this.fetchTrackList();
  }

}
</script>

<style>
.tracklist-container {
  min-height: 100vh;
  max-height: 100vh;
  overflow-y: scroll;
  overflow-x: hidden;
  min-width: 30%;
}

.icon-rpad {
  padding-right: 1vh;
}

.icon-medium {
  font-size: 24px;
  vertical-align: text-bottom;
}

.icon-large {
  font-size: 32px;
  vertical-align: bottom;
}

.card-header ion-icon, .card-header small {
  color: gray;
}

.card-body {
  padding-bottom: 10vh;
  padding-left: 1vh;
  padding-right: 1vh;
}
</style>