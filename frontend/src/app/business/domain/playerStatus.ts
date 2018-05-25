export class PlayerStatus {
  constructor(public volume: number,
              public song: number,
              public playlist: number[],
              public seconds: number,
              public isPlaying: boolean) {}
}
