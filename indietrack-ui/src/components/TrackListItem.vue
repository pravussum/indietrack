<template>
  <div class="collapse-fast">
    <div><ion-icon name="analytics"  class="icon-medium icon-rpad"></ion-icon>{{fmt2Digits(track.distance / 1000)}} km</div>
    <div><ion-icon name="stopwatch"  class="icon-medium icon-rpad"></ion-icon>{{fmtMinutes(getDurationInMin(track))}}</div>
    <div><ion-icon name="speedometer"  class="icon-medium icon-rpad"></ion-icon>{{fmtKilometers(getAvgInKmh(track))}}</div>

    <height-profile></height-profile>

<!--    <height-profile v-if="track === selectedTrack" :track="track" v-on:track-point-selected="onTrackPointSelectedInHeightGraph($event)"></height-profile>-->

    <!--&lt;!&ndash;              <button *ngIf="selectedTrack && !segCreationMode" class="btn btn-outline-secondary" type="button" (click)="onCreateSegment()">Create segment</button>&ndash;&gt;-->
    <!--&lt;!&ndash;              <div *ngIf="segCreationMode">&ndash;&gt;-->
    <!--&lt;!&ndash;                <input  [(ngModel)]="segmentName" class="form-control" type="text" placeholder="Segment name">&ndash;&gt;-->
    <!--&lt;!&ndash;                <button *ngIf="segCreationMode" class="btn btn-primary btn-block" type="button" (click)="saveSegment()">Save</button>&ndash;&gt;-->
    <!--&lt;!&ndash;              </div>&ndash;&gt;-->
    <!--&lt;!&ndash;              <app-track-segment-details *ngIf="track == selectedTrack" trackId="{{track.id}}"></app-track-segment-details>&ndash;&gt;-->
    <!--&lt;!&ndash;            </div>&ndash;&gt;-->
    <!--&lt;!&ndash;          </div>&ndash;&gt;-->

  </div>
</template>

<script>
import HeightProfile from "@/components/HeightProfile";
export default {
  components: {HeightProfile},
  props:['track'],
  name: "TrackListItem",
  data: () => {
    return {
      eventListenersBound : false
    }
  },
  watch: {
    track(newTrack, oldTrack) {
      console.log("changing track from " + oldTrack + " to " + newTrack);
    }
  },
  methods: {
    domElementUpdate(el) {
      let comp = this
      if(!this.eventListenersBound) {
        el.addEventListener('shown.bs.collapse', function () {
          comp.$emit('shown')
        });
        this.eventListenersBound = true;
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
    fmt2Digits(value) {
      if(!value) return '';
      const nf = new Intl.NumberFormat(undefined, {maximumFractionDigits: 2} );
      return nf.format(value);
    },
    fmtMinutes(value) {
      if(!value) return '';
      const nf = new Intl.NumberFormat(undefined, {style: 'unit', unit: 'minute', maximumFractionDigits: 0} );
      return nf.format(value);
    },
    fmtKilometers(value) {
      if(!value) return '';
      const nf = new Intl.NumberFormat(undefined, {style: 'unit', unit: 'kilometer-per-hour', maximumFractionDigits: 1} );
      return nf.format(value);
    }
  }
}
</script>

<style scoped>

.collapse-fast {
  transition: 0.1s;
}
</style>