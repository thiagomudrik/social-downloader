# social_core.py - This na the brain. No need to edit.
# HomeActivity go call this functions.

import re
import yt_dlp

def detect_platform(url: str) -> str:
    url = url.lower()
    if "facebook.com" in url or "fb.watch" in url:
        return "facebook"
    if "instagram.com" in url:
        return "instagram"
    if "tiktok.com" in url:
        return "tiktok"
    if "youtube.com" in url or "youtu.be" in url:
        return "youtube"
    if "x.com" in url or "twitter.com" in url:
        return "x"
    return "unknown"

def get_video_info(url: str) -> dict:
    # Use yt-dlp to get title and size without downloading
    ydl_opts = {
        'quiet': True,
        'no_warnings': True,
        'skip_download': True,
    }
    try:
        with yt_dlp.YoutubeDL(ydl_opts) as ydl:
            info = ydl.extract_info(url, download=False)
            return {
                "title": info.get('title', 'Unknown'),
                "size": str(info.get('filesize_approx', 'Unknown')),
                "url": url
            }
    except Exception as e:
        return {"title": f"Error: {e}", "size": "0", "url": url}

def download_video(url: str, platform: str) -> str:
    # This go download to /sdcard/SOCIAL/
    ydl_opts = {
        'outtmpl': f'/sdcard/SOCIAL/{platform}/%(title)s.%(ext)s',
        'quiet': False,
    }
    try:
        with yt_dlp.YoutubeDL(ydl_opts) as ydl:
            ydl.download([url])
        return "SUCCESS"
    except Exception as e:
        return f"FAILED: {e}"