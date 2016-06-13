package de.krien.games.madness.util;

public class Vector3i {

        public int x;
        public int y;
        public int z;

        public Vector3i() {
        }

        public Vector3i(int x, int y, int z) {
            this.set(x, y, z);
        }

        public void set(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void set(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public int lengthSquared() {
            return this.x * this.x + this.y * this.y + this.z * this.z;
        }

        public Vector3i translate(int x, int y, int z) {
            this.x += x;
            this.y += y;
            this.z += z;
            return this;
        }

        public static Vector3i add(Vector3i left, Vector3i right, Vector3i dest) {
            if(dest == null) {
                return new Vector3i(left.x + right.x, left.y + right.y, left.z + right.z);
            } else {
                dest.set(left.x + right.x, left.y + right.y, left.z + right.z);
                return dest;
            }
        }

        public static Vector3i sub(Vector3i left, Vector3i right, Vector3i dest) {
            if(dest == null) {
                return new Vector3i(left.x - right.x, left.y - right.y, left.z - right.z);
            } else {
                dest.set(left.x - right.x, left.y - right.y, left.z - right.z);
                return dest;
            }
        }

        public static Vector3i cross(Vector3i left, Vector3i right, Vector3i dest) {
            if(dest == null) {
                dest = new Vector3i();
            }

            dest.set(left.y * right.z - left.z * right.y, right.x * left.z - right.z * left.x, left.x * right.y - left.y * right.x);
            return dest;
        }

        public Vector3i negate() {
            this.x = -this.x;
            this.y = -this.y;
            this.z = -this.z;
            return this;
        }

        public Vector3i negate(Vector3i dest) {
            if(dest == null) {
                dest = new Vector3i();
            }

            dest.x = -this.x;
            dest.y = -this.y;
            dest.z = -this.z;
            return dest;
        }

        public static int dot(Vector3i left, Vector3i right) {
            return left.x * right.x + left.y * right.y + left.z * right.z;
        }

        public Vector3i scale(int scale) {
            this.x *= scale;
            this.y *= scale;
            this.z *= scale;
            return this;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(64);
            sb.append("Vector3i[");
            sb.append(this.x);
            sb.append(", ");
            sb.append(this.y);
            sb.append(", ");
            sb.append(this.z);
            sb.append(']');
            return sb.toString();
        }

        public final int getX() {
            return this.x;
        }

        public final int getY() {
            return this.y;
        }

        public final void setX(int x) {
            this.x = x;
        }

        public final void setY(int y) {
            this.y = y;
        }

        public void setZ(int z) {
            this.z = z;
        }

        public int getZ() {
            return this.z;
        }

        public boolean equals(Object obj) {
            if(this == obj) {
                return true;
            } else if(obj == null) {
                return false;
            } else if(this.getClass() != obj.getClass()) {
                return false;
            } else {
                Vector3i other = (Vector3i)obj;
                return this.x == other.x && this.y == other.y && this.z == other.z;
            }
        }

}
