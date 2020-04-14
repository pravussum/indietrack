import {LatLngBounds} from "leaflet";

export class Track {
  id: number;
  name: string;
  startTime: Date;
  endTime: Date;
  boundaries: LatLngBounds;
  distance: number;
}
