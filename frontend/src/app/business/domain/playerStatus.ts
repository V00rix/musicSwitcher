export class PlayerStatus {
  constructor(public volume: number,
              public song: number,
              public playlist: number[],
              public seek: {key: number, value: number},
              public isPlaying: boolean) {
  }
}
