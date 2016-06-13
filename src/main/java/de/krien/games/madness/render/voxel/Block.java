package de.krien.games.madness.render.voxel;

public class Block {

    private boolean isActive;
    private boolean isSelected;
    private BlockType blockType;

    private float[] topSurface;
    private float[] bottomSurface;
    private float[] frontSurface;
    private float[] backSurface;
    private float[] leftSurface;
    private float[] rightSurface;

    private float[] topSurfaceTexture;
    private float[] bottomSurfaceTexture;
    private float[] frontSurfaceTexture;
    private float[] backSurfaceTexture;
    private float[] leftSurfaceTexture;
    private float[] rightSurfaceTexture;

    public Block(BlockType blockType) {
        this.blockType = blockType;
        this.isActive = true;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public BlockType getBlockType() {
        return blockType;
    }

    public void setBlockType(BlockType blockType) {
        this.blockType = blockType;
    }

    public float[] getTopSurface() {
        return topSurface;
    }

    public void setTopSurface(float[] topSurface) {
        this.topSurface = topSurface;
    }

    public float[] getBottomSurface() {
        return bottomSurface;
    }

    public void setBottomSurface(float[] bottomSurface) {
        this.bottomSurface = bottomSurface;
    }

    public float[] getFrontSurface() {
        return frontSurface;
    }

    public void setFrontSurface(float[] frontSurface) {
        this.frontSurface = frontSurface;
    }

    public float[] getBackSurface() {
        return backSurface;
    }

    public void setBackSurface(float[] backSurface) {
        this.backSurface = backSurface;
    }

    public float[] getLeftSurface() {
        return leftSurface;
    }

    public void setLeftSurface(float[] leftSurface) {
        this.leftSurface = leftSurface;
    }

    public float[] getRightSurface() {
        return rightSurface;
    }

    public void setRightSurface(float[] rightSurface) {
        this.rightSurface = rightSurface;
    }

    public float[] getTopSurfaceTexture() {
        return topSurfaceTexture;
    }

    public void setTopSurfaceTexture(float[] topSurfaceTexture) {
        this.topSurfaceTexture = topSurfaceTexture;
    }

    public float[] getBottomSurfaceTexture() {
        return bottomSurfaceTexture;
    }

    public void setBottomSurfaceTexture(float[] bottomSurfaceTexture) {
        this.bottomSurfaceTexture = bottomSurfaceTexture;
    }

    public float[] getFrontSurfaceTexture() {
        return frontSurfaceTexture;
    }

    public void setFrontSurfaceTexture(float[] frontSurfaceTexture) {
        this.frontSurfaceTexture = frontSurfaceTexture;
    }

    public float[] getBackSurfaceTexture() {
        return backSurfaceTexture;
    }

    public void setBackSurfaceTexture(float[] backSurfaceTexture) {
        this.backSurfaceTexture = backSurfaceTexture;
    }

    public float[] getLeftSurfaceTexture() {
        return leftSurfaceTexture;
    }

    public void setLeftSurfaceTexture(float[] leftSurfaceTexture) {
        this.leftSurfaceTexture = leftSurfaceTexture;
    }

    public float[] getRightSurfaceTexture() {
        return rightSurfaceTexture;
    }

    public void setRightSurfaceTexture(float[] rightSurfaceTexture) {
        this.rightSurfaceTexture = rightSurfaceTexture;
    }
}
