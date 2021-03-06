import {LatLngBounds} from "leaflet";

export interface Track {
  id: number;
  name: string;
  startTime: Date;
  endTime: Date;
  boundaries: LatLngBounds;
  distance: number;
}
