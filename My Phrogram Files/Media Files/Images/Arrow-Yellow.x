xof 0303txt 0032

// DirectX file
// Creator: Ultimate Unwrap3D v2.28
// Time: Sun Oct 01 20:41:53 2006

// Start of Templates

template VertexDuplicationIndices {
 <b8d65549-d7c9-4995-89cf-53a9a8b031e3>
 DWORD nIndices;
 DWORD nOriginalVertices;
 array DWORD indices[nIndices];
}

template FVFData {
 <b6e70a0e-8ef9-4e83-94ad-ecc8b0c04897>
 DWORD dwFVF;
 DWORD nDWords;
 array DWORD data[nDWords];
}

template Header {
 <3D82AB43-62DA-11cf-AB39-0020AF71E433>
 WORD major;
 WORD minor;
 DWORD flags;
}

template Vector {
 <3D82AB5E-62DA-11cf-AB39-0020AF71E433>
 FLOAT x;
 FLOAT y;
 FLOAT z;
}

template Coords2d {
 <F6F23F44-7686-11cf-8F52-0040333594A3>
 FLOAT u;
 FLOAT v;
}

template Matrix4x4 {
 <F6F23F45-7686-11cf-8F52-0040333594A3>
 array FLOAT matrix[16];
}

template ColorRGBA {
 <35FF44E0-6C7C-11cf-8F52-0040333594A3>
 FLOAT red;
 FLOAT green;
 FLOAT blue;
 FLOAT alpha;
}

template ColorRGB {
 <D3E16E81-7835-11cf-8F52-0040333594A3>
 FLOAT red;
 FLOAT green;
 FLOAT blue;
}

template IndexedColor {
 <1630B820-7842-11cf-8F52-0040333594A3>
 DWORD index;
 ColorRGBA indexColor;
}

template Material {
 <3D82AB4D-62DA-11cf-AB39-0020AF71E433>
 ColorRGBA faceColor;
 FLOAT power;
 ColorRGB specularColor;
 ColorRGB emissiveColor;
 [...]
}

template TextureFilename {
 <A42790E1-7810-11cf-8F52-0040333594A3>
 STRING filename;
}

template MeshFace {
 <3D82AB5F-62DA-11cf-AB39-0020AF71E433>
 DWORD nFaceVertexIndices;
 array DWORD faceVertexIndices[nFaceVertexIndices];
}

template MeshTextureCoords {
 <F6F23F40-7686-11cf-8F52-0040333594A3>
 DWORD nTextureCoords;
 array Coords2d textureCoords[nTextureCoords];
}

template MeshMaterialList {
 <F6F23F42-7686-11cf-8F52-0040333594A3>
 DWORD nMaterials;
 DWORD nFaceIndexes;
 array DWORD faceIndexes[nFaceIndexes];
 [Material]
}

template MeshNormals {
 <F6F23F43-7686-11cf-8F52-0040333594A3>
 DWORD nNormals;
 array Vector normals[nNormals];
 DWORD nFaceNormals;
 array MeshFace faceNormals[nFaceNormals];
}

template MeshVertexColors {
 <1630B821-7842-11cf-8F52-0040333594A3>
 DWORD nVertexColors;
 array IndexedColor vertexColors[nVertexColors];
}

template Mesh {
 <3D82AB44-62DA-11cf-AB39-0020AF71E433>
 DWORD nVertices;
 array Vector vertices[nVertices];
 DWORD nFaces;
 array MeshFace faces[nFaces];
 [...]
}

template FrameTransformMatrix {
 <F6F23F41-7686-11cf-8F52-0040333594A3>
 Matrix4x4 frameMatrix;
}

template Frame {
 <3D82AB46-62DA-11cf-AB39-0020AF71E433>
 [...]
}

template FloatKeys {
 <10DD46A9-775B-11cf-8F52-0040333594A3>
 DWORD nValues;
 array FLOAT values[nValues];
}

template TimedFloatKeys {
 <F406B180-7B3B-11cf-8F52-0040333594A3>
 DWORD time;
 FloatKeys tfkeys;
}

template AnimationKey {
 <10DD46A8-775B-11cf-8F52-0040333594A3>
 DWORD keyType;
 DWORD nKeys;
 array TimedFloatKeys keys[nKeys];
}

template AnimationOptions {
 <E2BF56C0-840F-11cf-8F52-0040333594A3>
 DWORD openclosed;
 DWORD positionquality;
}

template Animation {
 <3D82AB4F-62DA-11cf-AB39-0020AF71E433>
 [...]
}

template AnimationSet {
 <3D82AB50-62DA-11cf-AB39-0020AF71E433>
 [Animation]
}

// Start of Frames

Frame SceneRoot {
  FrameTransformMatrix {
   1.000000, 0.000000, 0.000000, 0.000000,
   0.000000, 1.000000, 0.000000, 0.000000,
   0.000000, 0.000000, 1.000000, 0.000000,
   0.000000, 0.000000, 0.000000, 1.000000;;
  }

   Frame NoFrameName0 {
      FrameTransformMatrix {
       1.000000, 0.000000, 0.000000, 0.000000,
       0.000000, 1.000000, 0.000000, 0.000000,
       0.000000, 0.000000, 1.000000, 0.000000,
       0.000000, 0.000000, 0.000000, 1.000000;;
      }

      Frame Body {
         FrameTransformMatrix {
          1.000000, 0.000000, 0.000000, 0.000000,
          0.000000, 1.000000, 0.000000, 0.000000,
          0.000000, 0.000000, 1.000000, 0.000000,
          0.000000, 0.000000, 0.000000, 1.000000;;
         }

      }
   }

   Frame Body0 {
      FrameTransformMatrix {
       1.000000, 0.000000, 0.000000, 0.000000,
       0.000000, 1.000000, 0.000000, 0.000000,
       0.000000, 0.000000, 1.000000, 0.000000,
       0.000000, 0.000000, 0.000000, 1.000000;;
      }

      Mesh staticMesh {
       79;
       0.232143; 0.143650; 0.987301;,
       0.452380; 0.015872; -0.626985;,
       0.239286; 0.143500; -1.574604;,
       0.232143; -0.134128; 0.987301;,
       0.239286; -0.131735; -1.574604;,
       0.452380; 0.015872; -0.626985;,
       0.239286; 0.143500; -1.574604;,
       0.054762; 0.011387; -1.574604;,
       -0.201189; 0.143500; -1.574604;,
       0.239286; -0.131735; -1.574604;,
       -0.201189; -0.131735; -1.574604;,
       -0.201189; 0.143500; -1.574604;,
       -0.428570; 0.015872; -0.626985;,
       -0.208332; 0.143650; 0.987301;,
       -0.201189; -0.131735; -1.574604;,
       -0.208332; -0.134128; 0.987301;,
       -0.428570; 0.015872; -0.626985;,
       -0.201189; 0.143500; -1.574604;,
       0.014841; 0.282540; -0.626985;,
       0.239286; 0.143500; -1.574604;,
       -0.208332; 0.143650; 0.987301;,
       0.232143; 0.143650; 0.987301;,
       0.014841; 0.282540; -0.626985;,
       -0.208332; -0.134128; 0.987301;,
       0.016107; -0.273017; -0.626985;,
       0.232143; -0.134128; 0.987301;,
       -0.201189; -0.131735; -1.574604;,
       0.239286; -0.131735; -1.574604;,
       0.016107; -0.273017; -0.626985;,
       0.232143; -0.134128; 0.987301;,
       0.452380; 0.015872; -0.626985;,
       0.232143; 0.143650; 0.987301;,
       0.239286; 0.143500; -1.574604;,
       0.452380; 0.015872; -0.626985;,
       0.239286; -0.131735; -1.574604;,
       0.239286; -0.131735; -1.574604;,
       -0.201189; 0.143500; -1.574604;,
       -0.201189; -0.131735; -1.574604;,
       -0.428570; 0.015872; -0.626985;,
       -0.201189; 0.143500; -1.574604;,
       -0.208332; 0.143650; 0.987301;,
       -0.428570; 0.015872; -0.626985;,
       -0.208332; -0.134128; 0.987301;,
       -0.208332; 0.143650; 0.987301;,
       0.014841; 0.282540; -0.626985;,
       -0.201189; 0.143500; -1.574604;,
       0.239286; 0.143500; -1.574604;,
       0.014841; 0.282540; -0.626985;,
       0.232143; 0.143650; 0.987301;,
       -0.201189; -0.131735; -1.574604;,
       0.016107; -0.273017; -0.626985;,
       -0.208332; -0.134128; 0.987301;,
       0.232143; -0.134128; 0.987301;,
       0.016107; -0.273017; -0.626985;,
       0.239286; -0.131735; -1.574604;,
       0.051594; 0.015869; 2.965085;,
       0.232143; 0.143650; 0.987301;,
       -0.208332; 0.143650; 0.987301;,
       0.051594; 0.015869; 2.965085;,
       -0.208332; -0.134128; 0.987301;,
       0.232143; -0.134128; 0.987301;,
       0.051594; 0.015869; 2.965085;,
       0.232143; -0.134128; 0.987301;,
       0.976211; 0.004758; 0.987305;,
       0.976211; 0.004758; 0.987305;,
       0.232143; -0.134128; 0.987301;,
       0.232143; 0.143650; 0.987301;,
       0.976211; 0.004758; 0.987305;,
       0.232143; 0.143650; 0.987301;,
       0.051594; 0.015869; 2.965085;,
       0.051594; 0.015869; 2.965085;,
       -0.208332; 0.143650; 0.987301;,
       -1.000000; 0.004758; 0.987305;,
       -1.000000; 0.004758; 0.987305;,
       -0.208332; 0.143650; 0.987301;,
       -0.208332; -0.134128; 0.987301;,
       -1.000000; 0.004758; 0.987305;,
       -0.208332; -0.134128; 0.987301;,
       0.051594; 0.015869; 2.965085;;
       28;
       3;0, 1, 2;,
       3;3, 4, 5;,
       3;6, 7, 8;,
       3;9, 10, 7;,
       3;11, 12, 13;,
       3;14, 15, 16;,
       3;17, 18, 19;,
       3;20, 21, 22;,
       3;23, 24, 25;,
       3;26, 27, 28;,
       3;29, 30, 31;,
       3;32, 33, 34;,
       3;35, 7, 6;,
       3;36, 7, 10;,
       3;37, 38, 39;,
       3;40, 41, 42;,
       3;43, 44, 45;,
       3;46, 47, 48;,
       3;49, 50, 51;,
       3;52, 53, 54;,
       3;55, 56, 57;,
       3;58, 59, 60;,
       3;61, 62, 63;,
       3;64, 65, 66;,
       3;67, 68, 69;,
       3;70, 71, 72;,
       3;73, 74, 75;,
       3;76, 77, 78;;

      MeshNormals {
       79;
       0.625887; 0.775392; 0.083869;,
       0.999564; 0.012102; -0.026949;,
       0.858313; 0.497412; -0.126019;,
       0.647563; -0.756918; 0.087965;,
       0.875303; -0.467398; -0.124027;,
       0.999564; 0.012102; -0.026949;,
       0.000000; 0.000000; -1.000000;,
       0.000000; 0.000000; -1.000000;,
       0.000000; 0.000000; -1.000000;,
       0.000000; 0.000000; -1.000000;,
       0.000000; 0.000000; -1.000000;,
       -0.852120; 0.505253; -0.136417;,
       -0.999359; 0.011674; -0.033847;,
       -0.860339; 0.503797; 0.077499;,
       -0.869151; -0.475929; -0.134419;,
       -0.641659; -0.762068; 0.086751;,
       -0.999359; 0.011674; -0.033847;,
       -0.279983; 0.956945; -0.076580;,
       -0.001475; 0.999868; -0.016197;,
       0.276928; 0.957962; -0.074967;,
       -0.243146; 0.968230; 0.058405;,
       0.235674; 0.932129; -0.274942;,
       -0.001475; 0.999868; -0.016197;,
       -0.235047; -0.933349; -0.271317;,
       -0.000283; -0.999851; -0.017242;,
       0.238067; -0.932356; -0.272097;,
       -0.280942; -0.956534; -0.078189;,
       0.280383; -0.956825; -0.076620;,
       -0.000283; -0.999851; -0.017242;,
       0.647563; -0.756918; 0.087965;,
       0.999564; 0.012102; -0.026949;,
       0.625887; 0.775392; 0.083869;,
       0.858313; 0.497412; -0.126019;,
       0.999564; 0.012102; -0.026949;,
       0.875303; -0.467398; -0.124027;,
       0.000000; 0.000000; -1.000000;,
       0.000000; 0.000000; -1.000000;,
       -0.869151; -0.475929; -0.134419;,
       -0.999359; 0.011674; -0.033847;,
       -0.852120; 0.505253; -0.136417;,
       -0.860339; 0.503797; 0.077499;,
       -0.999359; 0.011674; -0.033847;,
       -0.641659; -0.762068; 0.086751;,
       -0.243146; 0.968230; 0.058405;,
       -0.001475; 0.999868; -0.016197;,
       -0.279983; 0.956945; -0.076580;,
       0.276928; 0.957962; -0.074967;,
       -0.001475; 0.999868; -0.016197;,
       0.235674; 0.932129; -0.274942;,
       -0.280942; -0.956534; -0.078189;,
       -0.000283; -0.999851; -0.017242;,
       -0.235047; -0.933349; -0.271317;,
       0.238067; -0.932356; -0.272097;,
       -0.000283; -0.999851; -0.017242;,
       0.280383; -0.956825; -0.076620;,
       0.000000; 0.005588; 0.999984;,
       0.625887; 0.775392; 0.083869;,
       0.000000; 0.997919; 0.064474;,
       0.000000; 0.005588; 0.999984;,
       -0.641659; -0.762068; 0.086751;,
       0.647563; -0.756918; 0.087965;,
       0.060617; 0.005377; 0.998147;,
       0.238067; -0.932356; -0.272097;,
       0.403525; 0.001015; -0.914968;,
       0.403525; 0.001015; -0.914968;,
       0.238067; -0.932356; -0.272097;,
       0.235674; 0.932129; -0.274942;,
       0.403525; 0.001015; -0.914968;,
       0.235674; 0.932129; -0.274942;,
       0.060617; 0.005377; 0.998147;,
       0.060617; 0.005377; 0.998147;,
       -0.243146; 0.968230; 0.058405;,
       -0.388189; 0.001114; -0.921579;,
       -0.388189; 0.001114; -0.921579;,
       -0.000005; 0.000000; -1.000000;,
       -0.235047; -0.933349; -0.271317;,
       -0.388189; 0.001114; -0.921579;,
       -0.235047; -0.933349; -0.271317;,
       0.060617; 0.005377; 0.998147;;
       28;
       3;0, 1, 2;,
       3;3, 4, 5;,
       3;6, 7, 8;,
       3;9, 10, 7;,
       3;11, 12, 13;,
       3;14, 15, 16;,
       3;17, 18, 19;,
       3;20, 21, 22;,
       3;23, 24, 25;,
       3;26, 27, 28;,
       3;29, 30, 31;,
       3;32, 33, 34;,
       3;35, 7, 6;,
       3;36, 7, 10;,
       3;37, 38, 39;,
       3;40, 41, 42;,
       3;43, 44, 45;,
       3;46, 47, 48;,
       3;49, 50, 51;,
       3;52, 53, 54;,
       3;55, 56, 57;,
       3;58, 59, 60;,
       3;61, 62, 63;,
       3;64, 65, 66;,
       3;67, 68, 69;,
       3;70, 71, 72;,
       3;73, 74, 75;,
       3;76, 77, 78;;
      }

      MeshTextureCoords {
       79;
       0.279290; 0.578257;,
       0.634685; 0.635595;,
       0.843617; 0.581616;,
       0.852744; 0.580859;,
       0.288409; 0.579084;,
       0.496805; 0.637431;,
       0.823852; 0.723062;,
       0.864499; 0.752164;,
       0.920879; 0.723062;,
       0.823852; 0.783691;,
       0.920879; 0.783691;,
       0.288121; 0.581758;,
       0.497376; 0.637293;,
       0.852445; 0.577974;,
       0.395421; 0.974726;,
       0.958231; 0.933240;,
       0.599343; 0.900198;,
       0.108006; 0.850174;,
       0.318581; 0.800844;,
       0.107204; 0.753150;,
       0.106631; 0.748649;,
       0.107124; 0.845675;,
       0.463784; 0.795995;,
       0.107700; 0.843455;,
       0.465680; 0.802495;,
       0.110002; 0.746455;,
       0.106877; 0.748807;,
       0.107806; 0.845830;,
       0.318374; 0.794650;,
       0.108068; 0.825861;,
       0.465764; 0.781746;,
       0.106177; 0.764702;,
       0.109164; 0.824286;,
       0.322595; 0.792443;,
       0.108106; 0.763667;,
       0.823852; 0.783691;,
       0.920879; 0.723062;,
       0.104830; 0.825599;,
       0.320725; 0.802618;,
       0.107514; 0.765030;,
       0.958231; 0.922832;,
       0.599343; 0.950979;,
       0.958231; 0.984021;,
       0.190517; 0.575425;,
       0.545800; 0.635206;,
       0.754838; 0.579721;,
       0.288515; 0.579592;,
       0.497107; 0.638283;,
       0.852844; 0.582386;,
       0.252060; 0.579827;,
       0.460867; 0.636676;,
       0.816395; 0.578129;,
       0.205470; 0.578685;,
       0.561149; 0.634724;,
       0.769802; 0.576227;,
       0.552122; 0.963913;,
       0.988696; 1.003684;,
       0.988696; 0.906656;,
       0.990404; 0.960006;,
       0.553487; 0.902750;,
       0.553487; 0.999778;,
       0.222145; 0.111793;,
       0.399869; 0.512905;,
       0.556961; 0.457032;,
       0.975365; 0.454332;,
       0.927632; 0.294578;,
       0.873214; 0.322555;,
       0.610959; 0.446692;,
       0.671302; 0.291261;,
       0.276144; 0.101453;,
       0.178207; 0.086598;,
       0.208873; 0.525841;,
       0.385724; 0.534264;,
       0.969809; 0.265451;,
       0.890932; 0.106940;,
       0.841685; 0.143256;,
       0.988069; 0.578531;,
       0.906709; 0.421280;,
       0.494645; 0.577413;;
      }

      MeshVertexColors {
       79;
       0; 1.000000; 1.000000; 1.000000; 1.000000;,
       1; 1.000000; 1.000000; 1.000000; 1.000000;,
       2; 1.000000; 1.000000; 1.000000; 1.000000;,
       3; 1.000000; 1.000000; 1.000000; 1.000000;,
       4; 1.000000; 1.000000; 1.000000; 1.000000;,
       5; 1.000000; 1.000000; 1.000000; 1.000000;,
       6; 1.000000; 1.000000; 1.000000; 1.000000;,
       7; 1.000000; 1.000000; 1.000000; 1.000000;,
       8; 1.000000; 1.000000; 1.000000; 1.000000;,
       9; 1.000000; 1.000000; 1.000000; 1.000000;,
       10; 1.000000; 1.000000; 1.000000; 1.000000;,
       11; 1.000000; 1.000000; 1.000000; 1.000000;,
       12; 1.000000; 1.000000; 1.000000; 1.000000;,
       13; 1.000000; 1.000000; 1.000000; 1.000000;,
       14; 1.000000; 1.000000; 1.000000; 1.000000;,
       15; 1.000000; 1.000000; 1.000000; 1.000000;,
       16; 1.000000; 1.000000; 1.000000; 1.000000;,
       17; 1.000000; 1.000000; 1.000000; 1.000000;,
       18; 1.000000; 1.000000; 1.000000; 1.000000;,
       19; 1.000000; 1.000000; 1.000000; 1.000000;,
       20; 1.000000; 1.000000; 1.000000; 1.000000;,
       21; 1.000000; 1.000000; 1.000000; 1.000000;,
       22; 1.000000; 1.000000; 1.000000; 1.000000;,
       23; 1.000000; 1.000000; 1.000000; 1.000000;,
       24; 1.000000; 1.000000; 1.000000; 1.000000;,
       25; 1.000000; 1.000000; 1.000000; 1.000000;,
       26; 1.000000; 1.000000; 1.000000; 1.000000;,
       27; 1.000000; 1.000000; 1.000000; 1.000000;,
       28; 1.000000; 1.000000; 1.000000; 1.000000;,
       29; 1.000000; 1.000000; 1.000000; 1.000000;,
       30; 1.000000; 1.000000; 1.000000; 1.000000;,
       31; 1.000000; 1.000000; 1.000000; 1.000000;,
       32; 1.000000; 1.000000; 1.000000; 1.000000;,
       33; 1.000000; 1.000000; 1.000000; 1.000000;,
       34; 1.000000; 1.000000; 1.000000; 1.000000;,
       35; 1.000000; 1.000000; 1.000000; 1.000000;,
       36; 1.000000; 1.000000; 1.000000; 1.000000;,
       37; 1.000000; 1.000000; 1.000000; 1.000000;,
       38; 1.000000; 1.000000; 1.000000; 1.000000;,
       39; 1.000000; 1.000000; 1.000000; 1.000000;,
       40; 1.000000; 1.000000; 1.000000; 1.000000;,
       41; 1.000000; 1.000000; 1.000000; 1.000000;,
       42; 1.000000; 1.000000; 1.000000; 1.000000;,
       43; 1.000000; 1.000000; 1.000000; 1.000000;,
       44; 1.000000; 1.000000; 1.000000; 1.000000;,
       45; 1.000000; 1.000000; 1.000000; 1.000000;,
       46; 1.000000; 1.000000; 1.000000; 1.000000;,
       47; 1.000000; 1.000000; 1.000000; 1.000000;,
       48; 1.000000; 1.000000; 1.000000; 1.000000;,
       49; 1.000000; 1.000000; 1.000000; 1.000000;,
       50; 1.000000; 1.000000; 1.000000; 1.000000;,
       51; 1.000000; 1.000000; 1.000000; 1.000000;,
       52; 1.000000; 1.000000; 1.000000; 1.000000;,
       53; 1.000000; 1.000000; 1.000000; 1.000000;,
       54; 1.000000; 1.000000; 1.000000; 1.000000;,
       55; 1.000000; 1.000000; 1.000000; 1.000000;,
       56; 1.000000; 1.000000; 1.000000; 1.000000;,
       57; 1.000000; 1.000000; 1.000000; 1.000000;,
       58; 1.000000; 1.000000; 1.000000; 1.000000;,
       59; 1.000000; 1.000000; 1.000000; 1.000000;,
       60; 1.000000; 1.000000; 1.000000; 1.000000;,
       61; 1.000000; 1.000000; 1.000000; 1.000000;,
       62; 1.000000; 1.000000; 1.000000; 1.000000;,
       63; 1.000000; 1.000000; 1.000000; 1.000000;,
       64; 1.000000; 1.000000; 1.000000; 1.000000;,
       65; 1.000000; 1.000000; 1.000000; 1.000000;,
       66; 1.000000; 1.000000; 1.000000; 1.000000;,
       67; 1.000000; 1.000000; 1.000000; 1.000000;,
       68; 1.000000; 1.000000; 1.000000; 1.000000;,
       69; 1.000000; 1.000000; 1.000000; 1.000000;,
       70; 1.000000; 1.000000; 1.000000; 1.000000;,
       71; 1.000000; 1.000000; 1.000000; 1.000000;,
       72; 1.000000; 1.000000; 1.000000; 1.000000;,
       73; 1.000000; 1.000000; 1.000000; 1.000000;,
       74; 1.000000; 1.000000; 1.000000; 1.000000;,
       75; 1.000000; 1.000000; 1.000000; 1.000000;,
       76; 1.000000; 1.000000; 1.000000; 1.000000;,
       77; 1.000000; 1.000000; 1.000000; 1.000000;,
       78; 1.000000; 1.000000; 1.000000; 1.000000;;
      }

      MeshMaterialList {
       1;
       28;
       0,
       0,
       0,
       0,
       0,
       0,
       0,
       0,
       0,
       0,
       0,
       0,
       0,
       0,
       0,
       0,
       0,
       0,
       0,
       0,
       0,
       0,
       0,
       0,
       0,
       0,
       0,
       0;

       Material {
        1.000000; 1.000000; 1.000000; 1.000000;;
        10.000000;
        1.000000; 1.000000; 1.000000;;
        1.000000; 1.000000; 1.000000;;

        TextureFilename {
         "arrow-Yellow.jpg";
        }
       }

      }
     }
   }
}
