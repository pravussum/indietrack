import * as Leaflet from 'leaflet'
import 'd3'

declare module 'leaflet' {
  namespace control {
    export function heightgraph():any;
  }
}
